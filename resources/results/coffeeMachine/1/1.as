Abstract States (5 in 00:00.083):

q0 ≝ and(¬(p2 ≝ Balance < 60), ¬(p1 ≝ Balance=0), ¬(p0 ≝ and(Status=on, or(Balance > 50, Balance=50), AskCoffee=1, CoffeeLeft > 0)))
q1 ≝ and(p2 ≝ Balance < 60, ¬(p1 ≝ Balance=0), ¬(p0 ≝ and(Status=on, or(Balance > 50, Balance=50), AskCoffee=1, CoffeeLeft > 0)))
q3 ≝ and(p2 ≝ Balance < 60, p1 ≝ Balance=0, ¬(p0 ≝ and(Status=on, or(Balance > 50, Balance=50), AskCoffee=1, CoffeeLeft > 0)))
q4 ≝ and(¬(p2 ≝ Balance < 60), ¬(p1 ≝ Balance=0), p0 ≝ and(Status=on, or(Balance > 50, Balance=50), AskCoffee=1, CoffeeLeft > 0))
q5 ≝ and(p2 ≝ Balance < 60, ¬(p1 ≝ Balance=0), p0 ≝ and(Status=on, or(Balance > 50, Balance=50), AskCoffee=1, CoffeeLeft > 0))