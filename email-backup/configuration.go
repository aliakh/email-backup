package main

import (
    "flag"
    "strings"
    "errors"
)

type Flags struct {
    email string
    password string
    host string
    port int
    excludedExtensions []string
    includedFiles []string
    emailsTo []string
}

func Parse() (Flags, error) {
    flags := Flags {}

    flag.StringVar(&flags.email, "email", "", "email sender/recipient")
    flag.StringVar(&flags.password, "password", "", "email password")
    flag.StringVar(&flags.host, "host", "", "SMTP host")
    flag.IntVar(&flags.port, "port", 0, "SMTP port")

    var excludedExtensions string
    flag.StringVar(&excludedExtensions, "excluded_extensions", "", "excluded file extensions (optional)")

    var includedFiles string
    flag.StringVar(&includedFiles, "included_files", "", "included files (optional)")

    var emailsTo string
    flag.StringVar(&emailsTo, "emails_to", "", "additional recipients (optional)")

    flag.Parse()

    if (flags.email == "") {
        return flags, errors.New("missing required option: email")
    }
    if (flags.password == "") {
        return flags, errors.New("missing required option: password")
    }
    if (flags.host == "") {
        return flags, errors.New("missing required option: host")
    }
    if (flags.port == 0) {
        return flags, errors.New("missing required option: port")
    }

    if (excludedExtensions != "") {
        flags.excludedExtensions = strings.Split(excludedExtensions, ",")
    }

    if (includedFiles != "") {
        flags.includedFiles = strings.Split(includedFiles, ",")
    }

    if (emailsTo != "") {
        flags.emailsTo = strings.Split(emailsTo, ",")
    }

    return flags, nil
}
