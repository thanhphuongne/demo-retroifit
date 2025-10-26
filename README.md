# Demo Retrofit Android App

This is a demo Android application that demonstrates the use of Retrofit for API calls in Kotlin.

## Features

- Fetches data from a REST API using Retrofit
- Parses JSON responses using Gson
- Displays data in a simple UI

## Prerequisites

- Android Studio (latest version recommended)
- Minimum SDK: 21
- Target SDK: 34

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on an emulator or device

## Dependencies

- Retrofit 2.9.0
- Gson Converter 2.9.0
- OkHttp Logging Interceptor 4.10.0
- AndroidX libraries

## API Endpoints

The app uses the following API endpoints:
- GET /posts - Retrieves a list of posts
- GET /users - Retrieves a list of users

## Project Structure

- `MainActivity.kt` - Main activity handling UI and API calls
- `ApiService.kt` - Retrofit API service interface
- `RetrofitClient.kt` - Singleton Retrofit client
- `Post.kt` - Data model for posts
- `User.kt` - Data model for users

## License

This project is for demonstration purposes only.