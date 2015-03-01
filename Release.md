Release Build
=============

To make a release make sure you have `gradle.properties` in the root of `birudo/mobile` module
with the following content.

**gradle.properties**
```
releaseKeyStore=<key_store_file>
releaseKeyPassword=<key_password>
releaseKeyStorePassword=<key_store_password>
releaseKeyAlias=key_alias
gPlaystoreServiceAccountEmailAddress=<playstore_service_account_email>
gPlaystorePKFile=<google-playstore-pk-file.p12>
```

A typical `gradle.properties` content should look like this:
```
releaseKeyStore=/home/username/.android/debug.keystore
releaseKeyStorePassword=android
releaseKeyAlias=androiddebugkey
releaseKeyPassword=android
feedbackEmail=feedback-email@example.com
gPlaystoreServiceAccountEmailAddress=9323892392132-842jajdkdadummy@developer.gserviceaccount.com
gPlaystorePKFile=/home/username/pdummy-pk-file.p12
```

Then in the project's root directory, issue:

`./release major milestone alpha`

This should build the app, version it, create a tag and push it to the remote repo and publish
to the Google Playstore's alpha track.