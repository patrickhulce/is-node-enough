#!/bin/sh

export GOPATH=$(pwd)
go get gopkg.in/redis.v5
go get github.com/nfnt/resize
