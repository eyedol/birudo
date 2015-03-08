Birudo uses Google Cloud Messaging platform for receiving build notifications from the Jenkins server.
It depends on the [GCM Notification Plugin][1] for Jenkins so you will have to configure it first.

Configure GCM Notification Plugin For Jenkins
=============================================
Follow the instruction on the [plugin][1] page to configure it for your Jenkins install.
**Note:** You will have to do this setup once.


Configure Android Device
========================
In order to receive notifications from the Jenkins server, your Android devices needs to be
configured to communicate with the Jenkins server. Follow the instructions below.

1. On your Jenkins server, go to your account page, click on your username then configure it by clicking on `Configure`
2. Look for the GCM Token field and click on the `Show QR Code` this should give you a popup with a QR code.
3. On your device, launch Birudo and go to `Settings`, then tap on `Scan QR Code`
4. Scan the QR code on your computer screen and wait till you see a successfully registered message.

That is it. Make sure your phone can reach the Jenkins server.

[1]: https://wiki.jenkins-ci.org/display/JENKINS/GCM+Notification+Plugin