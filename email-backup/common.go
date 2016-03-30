package main

import (
    "io"
)

func contains(items []string, i string) bool {
    for _, item := range items {
        if item == i {
            return true
        }
    }
    return false
}

func copy(reader io.Reader, writer io.Writer) error {
    buff := make([]byte, 4096)
    for {
        length, err := reader.Read(buff[:cap(buff)])
        if err != nil {
            if err != io.EOF {
                return err
            }
            if length == 0 {
                break
            }
        }

        _, err = writer.Write(buff[:length])
        if err != nil {
            return err
        }
    }
    return nil
}
