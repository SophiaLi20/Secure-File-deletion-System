# Secure File Deletion System

## About This Project

This is a simple yet powerful tool designed to securely delete files so they can't be recovered. Built using Java and a user-friendly Swing GUI, this system makes it easy to select and remove sensitive files permanently.

## What It Does -

1. Permanently deletes files by overwriting them multiple times.

2. Delete single or multiple files in one go.

3. Set custom overwrite passes to enhance security (default is 3 passes).

4. Easy-to-use interface with clear instructions.

## How It Works

1. Pick a file or multiple files you want to delete.

2. Choose how many times the system should overwrite the files before deleting them.

3. Confirm deletion, and the system securely removes the files by overwriting them with random data before deletion.

## What You Need

1. Java installed on your system.

2. Basic knowledge of running Java applications.

## How to Use
1. Download and compile the code
```
javac SecureFileDeletionGUI.java
```
2. Run the application
```
java SecureFileDeletionGUI
```
3. Follow the on-screen prompts to select and delete files securely.
## Why This Matters

When you delete a file normally, it can still be recovered using special software. This tool makes recovery nearly impossible by repeatedly overwriting the file before deleting it.

## Important Note

While this tool helps make deleted files unrecoverable, for highly sensitive data, consider encrypting your files before deletion or using additional security tools.

## Created By
Unnati Bhardwaj
