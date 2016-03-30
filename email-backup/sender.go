package main

import (
    "strconv"
    "net/smtp"
    "github.com/jordan-wright/email"
)

func Send(zipName string, flags Flags) error {
    msg := email.NewEmail()

    msg.From = flags.email
    msg.To = []string{flags.email}
    msg.Subject = "Backup " + zipName
    msg.Text = []byte("")

    msg.AttachFile(zipName)

    addr := flags.host + ":" + strconv.Itoa(flags.port)
    auth := smtp.PlainAuth("", flags.email, flags.password, flags.host)
    return msg.Send(addr, auth)
}
