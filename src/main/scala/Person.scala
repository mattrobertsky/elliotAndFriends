/**
  * Created by matt on 19/06/17.
  */
abstract class Person {
  val id: String
  val name: String
}

class Customer extends Person {
  val rewardPoints: Int
}//
