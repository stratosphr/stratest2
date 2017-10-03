MACHINE threeBatteries

	CONSTS
		n ≝ 5

	VARS
		sw ∈ 1..n
		h ∈ {tic, tac}

	FUNS
		bat : 1..n -> {ko, ok}
		bat_ : 1..n -> {ko, ok}

	INVARIANT
		and(
			and(
				n = 5
			)
			and(
				sw ∈ 1..n
				h ∈ {tic, tac}
			)
			and(
				bat!1 ∈ {ko, ok}
				bat!2 ∈ {ko, ok}
				bat!3 ∈ {ko, ok}
				bat!4 ∈ {ko, ok}
				bat!5 ∈ {ko, ok}
			)
			bat(sw) = ok
		)

	INITIALISATION
		h := tac ||
		sw := 1 ||
		bat(1) := ok ||
		bat(2) := ok ||
		bat(3) := ok ||
		bat(4) := ok ||
		bat(5) := ok

	EVENTS

		Tic ≝
			SELECT
				h = tac
			THEN
				h := tic
			END

		Commute ≝
			ANY
				i ∈ 1..n
				j ∈ 1..n
			WHERE
				and(
					i ≠ j
					bat(i) = ok
					bat(j) = ok
					h = tic
				)
			THEN
				ANY
					ns ∈ 1..n
				WHERE
					and(
						bat(ns) = ok
						ns ≠ sw
					)
				THEN
					h := tac ||
					sw := ns
				END
			END

		Fail ≝
			ANY
				i ∈ 1..n
				j ∈ 1..n
			WHERE
				and(
					i ≠ j
					bat(i) = ok
					bat(j) = ok
				)
			THEN
				ANY
					nb ∈ 1..n
				WHERE
					and(
						bat(nb) = ok
					)
				THEN
					CHOICE
						SELECT
							nb = sw
						THEN
							ANY
								ns ∈ 1..n
							WHERE
								and(
									ns ≠ sw
									bat(ns) = ok
								)
							THEN
								sw := ns ||
								bat(nb) := ko
							END
						END
					OR
						SELECT
							nb ≠ sw
						THEN
							bat(nb) := ko
						END
					END
				END
			END

		Repair ≝
			ANY
				nb ∈ 1..n
			WHERE
				and(
					bat(nb) = ko
				)
			THEN
				bat(nb) := ok
			END

