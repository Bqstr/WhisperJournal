package kz.bqstech.whisperJournal.util

class Exceptions {

    class UserInputException(override val message: String) : Exception() {}


    class UnauthorizedException : Exception() {
    }

    class ServerException : Exception() {
    }

    class UnknownException : Exception() {}

}