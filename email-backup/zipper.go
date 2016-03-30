package main

import (
    "fmt"
    "os"
    "strconv"
    "archive/zip"
    "path/filepath"
    "strings"
)

type Zipper struct {
    zipName string
    flags Flags
    folderName string
    writer *zip.Writer
}

func Zip(zipName string, flags Flags, folderName string) error {
    zipper := &Zipper{
        zipName: zipName,
        flags: flags,
        folderName: folderName,
    }
    return zipper.ZipFolder()
}

func (zipper *Zipper) ZipFolder() error {
    zipFile, err := os.Create(zipper.zipName)
    if err != nil {
        return err
    }
    defer zipFile.Close()

    zipper.writer = zip.NewWriter(zipFile)
    err = filepath.Walk(zipper.folderName, zipper.ZipFile)
    if err != nil {
        return err
    }

    err = zipper.writer.Close()
    if err != nil {
        return err
    }

    fileInfo, err := zipFile.Stat()
    if err != nil {
        return err
    }

    fmt.Println("Archive size: " + strconv.FormatInt(fileInfo.Size(), 10) + " byte(s)")
    return nil
}

func (zipper *Zipper) ZipFile(path string, fileInfo os.FileInfo, err error) error {
    if err != nil {
        return err
    }

    if !fileInfo.Mode().IsRegular() {
        return nil
    }

    reader, err := os.Open(path)
    if err != nil {
        return err
    }
    defer reader.Close()

    fileName := strings.TrimPrefix(path, zipper.folderName+string(os.PathSeparator))
    if exclude(zipper, fileName) {
        fmt.Println("skip " + fileName)
        return nil
    }
    fmt.Println("add  " + fileName)

    writer, err := zipper.writer.Create(fileName)
    if err != nil {
        return err
    }

    return copy(reader, writer)
}

func exclude(zipper *Zipper, fileName string) bool {
    if fileName == zipper.zipName {
        return true
    } else if contains(zipper.flags.includedFiles, fileName) {
        return false
    } else {
        i := strings.LastIndex(fileName, ".")
        if i < 0 {
            return false
        } else {
            extension := fileName[i+1:]
            return contains(zipper.flags.excludedExtensions, extension)
        }
    }
}
