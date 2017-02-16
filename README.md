# Response Times

## Redis
`siegem -d0 -r 100 -c 100 -H 'Content-Type: application/json' --data '{"id":123,"name":"foobar","name_2":"bazbam","metadata":{"name":"name","val":"val"}}' http://localhost:8200/redis`

### go - `net/http`
```
Transactions:                  10000
Availability:                    100 %
Elapsed time:                   9.89 s
Response time:                98.135 ms
Transaction rate:           1011.122 trans/sec
Average Concurrency:          99.323
Successful transactions:       10000
Failed transactions:               0
Longest transaction:             467 ms
90th percentile:                 109 ms
50th percentile:                  94 ms
Shortest transaction:             42 ms
```

### node.js
```
Transactions:                  10000
Availability:                    100 %
Elapsed time:                 13.809 s
Response time:               137.165 ms
Transaction rate:            724.165 trans/sec
Average Concurrency:          99.424
Successful transactions:       10000
Failed transactions:               0
Longest transaction:             314 ms
90th percentile:                 160 ms
50th percentile:                 132 ms
Shortest transaction:             64 ms
```

### scala - `JsValue`
```
Transactions:                  10000
Availability:                    100 %
Elapsed time:                 16.539 s
Response time:               164.483 ms
Transaction rate:            604.631 trans/sec
Average Concurrency:          99.595
Successful transactions:       10000
Failed transactions:               0
Longest transaction:            2794 ms
90th percentile:                 163 ms
50th percentile:                 138 ms
Shortest transaction:              2 ms
```

### python
```
Transactions:                  10000
Availability:                    100 %
Elapsed time:                 19.249 s
Response time:               191.227 ms
Transaction rate:            519.508 trans/sec
Average Concurrency:          99.444
Successful transactions:       10000
Failed transactions:               0
Longest transaction:             482 ms
90th percentile:                 212 ms
50th percentile:                 187 ms
Shortest transaction:             73 ms
```

### scala - native objects
```
Transactions:                  10000
Availability:                    100 %
Elapsed time:                 20.429 s
Response time:               203.233 ms
Transaction rate:              489.5 trans/sec
Average Concurrency:          99.532
Successful transactions:       10000
Failed transactions:               0
Longest transaction:            1002 ms
90th percentile:                 277 ms
50th percentile:                 187 ms
Shortest transaction:             29 ms
```

## Image Processing
`siegem -d0 -r 50 -c 20 -H 'Content-Type: image/jpeg' -X POST --data @test-file.jpg <URL>`

### node.js - `sharp`
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                 29.618 s
Response time:               497.207 ms
Transaction rate:             33.763 trans/sec
Average Concurrency:          16.864
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            5183 ms
90th percentile:                 722 ms
50th percentile:                 487 ms
Shortest transaction:             91 ms
```

### go - `net/http`, `vips`
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                 32.057 s
Response time:               638.557 ms
Transaction rate:             31.194 trans/sec
Average Concurrency:          19.931
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            1130 ms
90th percentile:                 762 ms
50th percentile:                 639 ms
Shortest transaction:             65 ms
```

### python - `Pillow`
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                101.028 s
Response time:              2006.634 ms
Transaction rate:              9.898 trans/sec
Average Concurrency:          19.862
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            2561 ms
90th percentile:                2298 ms
50th percentile:                2023 ms
Shortest transaction:            924 ms
```

### go - `net/http`, `resize`
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                101.132 s
Response time:              2013.973 ms
Transaction rate:              9.888 trans/sec
Average Concurrency:          19.916
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            2616 ms
90th percentile:                2245 ms
50th percentile:                2015 ms
Shortest transaction:           1032 ms
```

### scala - `scrimage`
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                114.436 s
Response time:              2270.326 ms
Transaction rate:              8.739 trans/sec
Average Concurrency:          19.851
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            5089 ms
90th percentile:                2806 ms
50th percentile:                2211 ms
Shortest transaction:            877 ms
```

### go - `net/http`, `imaging`
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                125.609 s
Response time:              2500.344 ms
Transaction rate:              7.961 trans/sec
Average Concurrency:          19.908
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            4525 ms
90th percentile:                2976 ms
50th percentile:                2442 ms
Shortest transaction:           1553 ms
```

### node.js - `gm` (`imagemagick`)
```
Transactions:                   1000
Availability:                    100 %
Elapsed time:                143.351 s
Response time:              2856.359 ms
Transaction rate:              6.976 trans/sec
Average Concurrency:           19.93
Successful transactions:        1000
Failed transactions:               0
Longest transaction:            3355 ms
90th percentile:                3018 ms
50th percentile:                2848 ms
Shortest transaction:           1810 ms
```

### node.js - `jimp`
```
Transactions:                    221
Availability:                    100 %
Elapsed time:                122.542 s
Response time:             10018.122 ms
Transaction rate:              1.803 trans/sec
Average Concurrency:          19.597
Successful transactions:         221
Failed transactions:               0
Longest transaction:           29603 ms
90th percentile:               19821 ms
50th percentile:                8072 ms
Shortest transaction:           3092 ms
```
