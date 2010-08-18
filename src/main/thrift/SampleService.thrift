namespace java com.baskok.thriftsample.generated

struct User {
  1: string username
}

service HelloService {
  string say_hello(1: User user)
}