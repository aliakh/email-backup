package main

import (
    "fmt"
    "os"
)

func main() {
    folderName, err := CurrentFolderName()
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    flags, err := Parse() 
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    zipName := ZipName(folderName)
    fmt.Println("Archive name: " + zipName)

    fmt.Println("Zipping started")
    err = Zip(zipName, flags, folderName)
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }
    fmt.Println("Zipping finished")

    fmt.Println("Sending started")
    err = Send(zipName, flags);
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }
    fmt.Println("Sending finished")
}
