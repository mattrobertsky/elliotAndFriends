/**
  * Created by matt on 20/06/17.
  */
class Customer(name: String) extends Person(name) {
  var rewardPoints: Int = 0
  val id = nextId()

  override def nextId(): String = {
    seq += 1
    "CUS-" + seq
  }

}

