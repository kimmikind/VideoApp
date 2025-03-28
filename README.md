## Video Player App
Приложение позволяет получить список видео с помощью Youtube API. На главном экране отображается 10 видео, при отсутствии интернета, список подгружается из БД Room. 
При нажатии на видео из списка открывается экран просмотра, экран содержит кнопки управления воспроизведением и перехода в полноэкранным режим.
## Архитектура 
MVVM
## Библиотеки 
Compose, Compose Navigation, Coil, Gson, Retrofit, Kotlin Coroutines, Exoplayer, Room, Hilt.
## Инструкция по запуску
1. Клонируйте репозиторий:
   ```bash
   git clone https://gitfront.io/r/kimmikind/x7Qc7KsYhet6/VideoApp.git
3. Откройте проект в Android Studio.
4. Запустите приложение на эмуляторе или устройстве.

- **Система сборки**: Gradle (Kotlin DSL)
- **Версия Gradle**: 8.10.2
- **Версия Android Gradle Plugin**: 8.8.1
- **Целевая версия SDK**: Android 15 (API 35)

Ссылка на apk-файл на диске: https://disk.yandex.ru/d/rMSXZJr4F8NfpA
## Возникшие сложности
Youtube API напрямую не предоставляет ссылки на видео форматом .MP4, которые можно было бы использовать для ExoPlayer. 
В комментарий также добавлен вариант с возможностью отображения видео через стороннюю библиотеку androidyoutubeplayer. 
В приложении оставлен вариант через ExoPlayer и видео-заглушкой, чтобы продемонстрировать работоспособность приложения по заданию.
Пересмотрела API, которые могли бы предоставить напрямую ссылки на видео, но таковых не было найдено. 
