class Customer(override val id: String, override val name: String) extends Person(id,name) {
  var rewardPoints: Int = 0
}
//Test