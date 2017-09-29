Abstract States (5 in 00:00.098):

q0 ≝ and(¬(p1 ≝ Status=on), ¬(p2 ≝ and(Status=off, CoffeeLeft > 0, or(MaxPot > Pot, MaxPot=Pot))), ¬(p0 ≝ and(Status=off, or(Pot > (MaxPot - 50), Pot=(MaxPot - 50)))))
q1 ≝ and(¬(p1 ≝ Status=on), p2 ≝ and(Status=off, CoffeeLeft > 0, or(MaxPot > Pot, MaxPot=Pot)), ¬(p0 ≝ and(Status=off, or(Pot > (MaxPot - 50), Pot=(MaxPot - 50)))))
q2 ≝ and(p1 ≝ Status=on, ¬(p2 ≝ and(Status=off, CoffeeLeft > 0, or(MaxPot > Pot, MaxPot=Pot))), ¬(p0 ≝ and(Status=off, or(Pot > (MaxPot - 50), Pot=(MaxPot - 50)))))
q4 ≝ and(¬(p1 ≝ Status=on), ¬(p2 ≝ and(Status=off, CoffeeLeft > 0, or(MaxPot > Pot, MaxPot=Pot))), p0 ≝ and(Status=off, or(Pot > (MaxPot - 50), Pot=(MaxPot - 50))))
q5 ≝ and(¬(p1 ≝ Status=on), p2 ≝ and(Status=off, CoffeeLeft > 0, or(MaxPot > Pot, MaxPot=Pot)), p0 ≝ and(Status=off, or(Pot > (MaxPot - 50), Pot=(MaxPot - 50))))