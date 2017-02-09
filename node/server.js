const fs = require('fs')
const app = require('express')()
const bodyParser = require('body-parser')
const redis = require('redis')
const sharp = require('sharp')
const Jimp = require('jimp')
const gm = require('gm').subClass({imageMagick: true});

app.use(bodyParser.raw({type: 'image/*', limit: '10mb'}))
app.use(bodyParser.json())
app.post('/image-native', (req, res) => {
  console.log('received', new Date().toISOString())
  sharp(req.body)
    .resize(200, 200)
    .toBuffer()
    .then(fixed => {
      res.set('content-type', 'image/jpeg')
      res.status(200);
      res.send(fixed);
    })
    .catch(err => {
      console.log(err.stack);
      res.status(500);
      res.json({failed: true});
    })
})

app.post('/image-magick', (req, res) => {
  console.log('received', new Date().toISOString())
  res.set('content-type', 'image/jpeg')
  gm(req.body, 'image.jpg')
    .resize(200, 200, '!')
    .noProfile()
    .quality(70)
    .stream('jpeg')
    .pipe(res);
})


app.post('/image-js', (req, res) => {
  console.log('received', new Date().toISOString())
  Jimp.read(req.body, (err, image) => {
    if (err) {
      console.log(err.stack)
      res.send(500)
      return;
    }
    image.resize(200, 200)
    image.quality(70)
    image.getBuffer(Jimp.MIME_JPEG, (err, fixed) => {
      if (err) {
        console.log(err.stack)
        res.send(500)
        return;
      }
      res.set('content-type', 'image/jpeg')
      res.status(200);
      res.send(fixed);
    })
  })
})

const client = redis.createClient()
app.post('/redis', (req, res) => {
  client.set('nodekey', JSON.stringify(req.body), () => {
    client.get('nodekey', (err, message) => {
      res.json(JSON.parse(message));
    })
  })
})

app.listen(8080)


process.on('uncaughtException', e => console.log(e.stack));
