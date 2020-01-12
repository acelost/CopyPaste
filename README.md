# CopyPaste

[![Bintray][bintraybadge-svg]][bintray-android-builder]

## How to use

Use `PasteRequests` class to build request to one of existing clipboard services:

```java
public void myFirstPaste() {
    PasteRequest request = PasteRequests.friendPasteComRequest("Hello, world!", null);
    CopyPaste.getInstance().paste(context, request);
}
```

Access link will be printed in LogCat:

<img src="copy-paste-log-output.png" width="800">

Use second parameter of `CopyPaste.paste` method to pass custom listener:

```java
public void myCustomListener() {
    PasteRequest request = PasteRequest.cl1pNetRequest(
            "Hello, world!",
            new PasteResultListener() {
                public void onSuccess(String accessLink) {
                    // handle success result
                }
                public void onFailure(Throwable error) {
                    // handle failure result
                }
            }
    );
    CopyPaste.getInstance().paste(context, request);
}
```

Also you can build request to any service you want:

```java
public void myCustomPaste() {
    PasteRequest request = PasteRequest.newBuilder("https://my-service.com", "my content")
            .addHeader("Content-Type", "text/html")
            .setParamsFormatter(new CustomParamsFormatter())
            .setPayloadFormatter(new CustomPayloadFormatter())
            .setPasteResultFormatter(new CustomResultFormatter())
            .build();
    CopyPaste.getInstance().paste(context, request);
}
```

## How to integrate

```groovy
implementation 'com.acelost.copy-paste:copy-paste:0.0.1'
```

## License

    Copyright 2019 The Spectrum Author

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[bintray-android-builder]: https://bintray.com/acelost/CopyPaste/copy-paste
[bintraybadge-svg]: https://img.shields.io/bintray/v/acelost/CopyPate/copy-paste.svg
