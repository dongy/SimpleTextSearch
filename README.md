## Overview
This is a simple Java application to search text in files in a directory. \
JDK 15.0.2 is used when developed the application.

## How to run the application
A directory "files" is created under the application root directory. In the directory we can put some files. \
In IntelliJ, I configure "./directoryName" in the "Program arguments" in the "Run Configuration". \
In the Project of IntelliJ, we can right-click Main and click "Run Main.main()" to run the application.

## Run example
```shell
search>to
file2: 100%
file1: 100%
search>to be
file1: 100%
file2: 50%
search>be to
file1: 100%
file2: 50%
search>to be not
file1: 100%
file2: 33%
search>not be to
file1: 100%
file2: 33%
search>cats
file2: 0%
file1: 0%
no matches found
search>:quit