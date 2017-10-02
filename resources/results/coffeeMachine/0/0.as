Abstract States (4 in 00:00.138):

q1 ≝ and(¬(p0 ≝ Status = off), ¬(p1 ≝ Status = on), p2 ≝ or(and(Status = on, AskChange = 0, AskCoffee = 0, Balance = 0), Status = error))
q2 ≝ and(¬(p0 ≝ Status = off), p1 ≝ Status = on, ¬(p2 ≝ or(and(Status = on, AskChange = 0, AskCoffee = 0, Balance = 0), Status = error)))
q3 ≝ and(¬(p0 ≝ Status = off), p1 ≝ Status = on, p2 ≝ or(and(Status = on, AskChange = 0, AskCoffee = 0, Balance = 0), Status = error))
q4 ≝ and(p0 ≝ Status = off, ¬(p1 ≝ Status = on), ¬(p2 ≝ or(and(Status = on, AskChange = 0, AskCoffee = 0, Balance = 0), Status = error)))