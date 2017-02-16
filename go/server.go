package main

import (
  "fmt"
	"io"
  "io/ioutil"
  "encoding/json"
	"net/http"
  "image"
  "image/jpeg"
  "bytes"
  "gopkg.in/redis.v5"
  "github.com/daddye/vips"
  "github.com/nfnt/resize"
)

type SubPayload struct {
  Name string `json:"name"`
  Value string `json:"val"`
}

type Payload struct {
  Id int `json:"id"`
  NameA string `json:"name"`
  NameB string `json:"name_2"`
  Metadata SubPayload `json:"metadata"`
}

var client *redis.Client

func hello(w http.ResponseWriter, r *http.Request) {
	io.WriteString(w, "Hello world!")
  fmt.Println("called!")
}

func resizevips(w http.ResponseWriter, r *http.Request) {
  body, err := ioutil.ReadAll(r.Body)
  if (err != nil) {
    panic(err)
  }

  options := vips.Options{
      Width:        200,
      Height:       200,
      Crop:         true,
      Interpolator: vips.BILINEAR,
      Gravity:      vips.CENTRE,
      Quality:      70,
  }
  out, err := vips.Resize(body, options)
  if (err != nil) {
    panic(err)
  }

  fmt.Println("doing stuff!")
  w.Header().Set("Content-Type", "image/jpeg")
  w.Write(out)
}

func resizeimage(w http.ResponseWriter, r *http.Request) {
  srcImage, imageType, err := image.Decode(r.Body)
  if (err != nil) {
    panic(err)
  }

  destImage := resize.Resize(200, 200, srcImage, resize.Bilinear)
  // destImage := imaging.Fill(srcImage, 200, 200, imaging.Center, imaging.Linear)
  buffer := new(bytes.Buffer)
  err = jpeg.Encode(buffer, destImage, nil)
  if (err != nil) {
    panic(err)
  }

  fmt.Println("image type: " + imageType)
  w.Header().Set("Content-Type", "image/jpeg")
  w.Write(buffer.Bytes())
}

func doredis(w http.ResponseWriter, r *http.Request) {
  if client == nil {
    client = redis.NewClient(&redis.Options{
        Addr:     "localhost:6379",
        Password: "", // no password set
        DB:       0,  // use default DB
    })
  }
  var p Payload
  err := json.NewDecoder(r.Body).Decode(&p)
  if (err != nil) {
    panic(err)
  }
  redis_payload, _ := json.Marshal(p)
  err = client.Set("gokey", redis_payload, 0).Err()
  if (err != nil) {
    panic(err)
  }

  val, err := client.Get("gokey").Result()
  if (err != nil) {
    panic(err)
  }

  var rp Payload
  w.Header().Set("Content-Type", "application/json")
  json.Unmarshal([]byte(val), &rp)
  json.NewEncoder(w).Encode(rp)
}

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/", hello)
	mux.HandleFunc("/image-vips", resizevips)
	mux.HandleFunc("/image", resizeimage)
	mux.HandleFunc("/redis", doredis)
	http.ListenAndServe(":8200", mux)
}
