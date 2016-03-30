package main

import (
    "time" 
    "testing"
    "github.com/stretchr/testify/assert"
)

func Test_zipNameSuffix(t *testing.T) {
    assert.Equal(t, " [01-01-1970 03.00.00].zip", zipNameSuffix(time.Unix(0,0))) 	
}