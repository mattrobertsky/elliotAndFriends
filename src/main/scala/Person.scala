/**
  * Created by matt on 19/06/17.
  */
abstract class Person {
  val id: String
  val name: String
//ghjghg
}

class Customer(val id: String, val name: String) extends Person {

  var rewardPoints: Int = 0
}

