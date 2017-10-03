MACHINE coffeeMachine

	CONSTS
		MaxPot ≝ 200
		MaxBal ≝ 200
		MaxCoffee ≝ 3

	VARS
		Pot ∈ {0, 50, 100, 150, 200, 250}
		Balance ∈ {0, 50, 100, 150, 200}
		CoffeeLeft ∈ 0..MaxCoffee
		Status ∈ {off, on, error}
		AskCoffee ∈ 0..1
		AskChange ∈ 0..1

	INVARIANT
		and(
			and(
				MaxPot = 200
				MaxBal = 200
				MaxCoffee = 3
			)
			and(
				Pot ∈ {0, 50, 100, 150, 200, 250}
				Balance ∈ {0, 50, 100, 150, 200}
				CoffeeLeft ∈ 0..MaxCoffee
				Status ∈ {off, on, error}
				AskCoffee ∈ 0..1
				AskChange ∈ 0..1
			)
			and(
				or(
					¬(Balance = 0)
					and(
						AskCoffee = 0
						AskChange = 0
					)
				)
				or(
					¬(AskChange = 1)
					and(
						AskCoffee = 0
						Balance > 0
					)
				)
				or(
					¬(AskCoffee = 1)
					and(
						AskChange = 0
						or(
							Balance > 50
							Balance = 50
						)
					)
				)
			)
		)

	INITIALISATION
		Status := off ||
		Pot := 0 ||
		Balance := 0 ||
		CoffeeLeft := 3 ||
		AskCoffee := 0 ||
		AskChange := 0

	EVENTS

		insert50 ≝
			SELECT
				and(
					Status = on
					AskChange = 0
					AskCoffee = 0
					or(
						MaxBal > (Balance + 50)
						MaxBal = (Balance + 50)
					)
				)
			THEN
				Balance := (Balance + 50)
			END

		insert100 ≝
			SELECT
				and(
					Status = on
					AskChange = 0
					AskCoffee = 0
					or(
						MaxBal > (Balance + 100)
						MaxBal = (Balance + 100)
					)
				)
			THEN
				Balance := (Balance + 100)
			END

		powerUp ≝
			SELECT
				and(
					Status = off
					CoffeeLeft > 0
					or(
						MaxPot > Pot
						MaxPot = Pot
					)
				)
			THEN
				Status := on ||
				Balance := 0 ||
				AskCoffee := 0 ||
				AskChange := 0
			END

		powerDown ≝
			SELECT
				or(
					and(
						Status = on
						AskChange = 0
						AskCoffee = 0
						Balance = 0
					)
					Status = error
				)
			THEN
				Status := off
			END

		autoOut ≝
			SELECT
				Status = on
			THEN
				Status := error
			END

		takePot ≝
			SELECT
				and(
					Status = off
					or(
						Pot > (MaxPot - 50)
						Pot = (MaxPot - 50)
					)
				)
			THEN
				Pot := 0
			END

		coffeeReq ≝
			SELECT
				and(
					Status = on
					or(
						Balance > 50
						Balance = 50
					)
					AskCoffee = 0
					AskChange = 0
				)
			THEN
				AskCoffee := 1
			END

		changeReq ≝
			SELECT
				and(
					Status = on
					Balance > 0
					AskCoffee = 0
					AskChange = 0
				)
			THEN
				AskChange := 1
			END

		addCoffee ≝
			ANY
				x ∈ 1..MaxCoffee
			WHERE
				or(
					MaxCoffee > (CoffeeLeft + x)
					MaxCoffee = (CoffeeLeft + x)
				)
			THEN
				SELECT
					and(
						Status = off
						MaxCoffee > CoffeeLeft
					)
				THEN
					CoffeeLeft := (CoffeeLeft + x)
				END
			END

		serveCoffee ≝
			SELECT
				and(
					Status = on
					or(
						Balance > 50
						Balance = 50
					)
					AskCoffee = 1
					CoffeeLeft > 0
				)
			THEN
				IF
					and(
						or(
							(Pot + 50) > MaxPot
							CoffeeLeft = 1
						)
						Balance > 50
					)
				THEN
					Status := error ||
					AskChange := 1 ||
					AskCoffee := 0 ||
					Balance := (Balance - 50) ||
					CoffeeLeft := (CoffeeLeft - 1) ||
					Pot := (Pot + 50)
				ELSE
					IF
						or(
							(Pot + 50) > MaxPot
							CoffeeLeft = 1
						)
					THEN
						Status := error ||
						AskCoffee := 0 ||
						Balance := (Balance - 50) ||
						CoffeeLeft := (CoffeeLeft - 1) ||
						Pot := (Pot + 50)
					ELSE
						IF
							Balance > 50
						THEN
							AskChange := 1 ||
							AskCoffee := 0 ||
							Balance := (Balance - 50) ||
							CoffeeLeft := (CoffeeLeft - 1) ||
							Pot := (Pot + 50)
						ELSE
							AskCoffee := 0 ||
							Balance := (Balance - 50) ||
							CoffeeLeft := (CoffeeLeft - 1) ||
							Pot := (Pot + 50)
						END
					END
				END
			END

		changeBack ≝
			SELECT
				and(
					Status = on
					Balance > 0
					AskChange = 1
				)
			THEN
				Balance := 0 ||
				AskChange := 0
			END

