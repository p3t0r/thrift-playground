namespace java com.baskok.thriftsample.generated

service SharedService {
  bool ping(),
  map<string,string> getStatus()
}

enum Location {
  WORK = 1,
  HOME = 2,
  MOBILE = 3
}

struct PhoneNumber {
  1: string number,
  2: optional Location location
}

struct User {
  1: string username,
  2: string emailAddress,
  /** users are by default not validated */
  3: bool validated = 0,
  4: optional list<PhoneNumber> phoneNumbers
}

exception UnauthorizedException {
  1: string message
}
exception UserNotFoundException {
  1: string message
}

service HelloService extends SharedService {
  User getUser(1: string email) throws (1: UnauthorizedException ue, 2: UserNotFoundException unfe),
  string hello(1: string userName),
}
