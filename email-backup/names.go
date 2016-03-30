package main

import (
    "time" 
    "strings" 
    "os" 
)

func CurrentFolderName() (string, error) {
    return os.Getwd();
}

func ZipName(folderName string) string {
    return zipNamePrefix(folderName) + zipNameSuffix(time.Now())
}

func zipNamePrefix(folderName string) string {
    i := strings.LastIndex(folderName, string(os.PathSeparator))
    if (i < 0) {
        return folderName
    } else {
        return folderName[i+1:len(folderName)]
    }
}

func zipNameSuffix(time time.Time) string {
    return " [" + time.Format("02-01-2006 15.04.05") + "].zip"
}