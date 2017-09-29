MACHINE line14

	CONSTS
		NS ≝ 2
		NS1 ≝ (NS - 1)
		NR ≝ 2

	SETS
		Rames ≝ 1..NR
		Stations ≝ 0..NS1

	FUNS
		Pos : Rames -> Stations
		Dir : Rames -> {-1, 1}
		Portes : Rames -> {ouvertes, fermees, refermees}
		Mvt : Rames -> 0..1
		Mvt_ : Rames -> 0..1
		Pos_ : Rames -> Stations
		Dir_ : Rames -> {-1, 1}
		Portes_ : Rames -> {ouvertes, fermees, refermees}

	INVARIANT
		and(
			and(
				NS=2
				NS1=(NS - 1)
				NR=2
			)
			and(
				Pos!1 ∈ Stations
				Pos!2 ∈ Stations
				Dir!1 ∈ {-1, 1}
				Dir!2 ∈ {-1, 1}
				Portes!1 ∈ {ouvertes, fermees, refermees}
				Portes!2 ∈ {ouvertes, fermees, refermees}
				Mvt!1 ∈ 0..1
				Mvt!2 ∈ 0..1
			)
			and(
				∀(R ∈ Rames).(and(
					Mvt(R)=1
				) ⇒ Portes(R)=fermees)
				∀(R ∈ Rames).(Portes(R)=ouvertes ⇒ Mvt(R)=0)
				∀(R ∈ Rames).(Portes(R)=refermees ⇒ Mvt(R)=0)
				∀(R ∈ Rames).(Dir(R) ≠ 0)
				∀(R1 ∈ Rames, R2 ∈ Rames).(R1 ≠ R2 ⇒ or(
					Dir(R1) ≠ Dir(R2)
					Pos(R1) ≠ Pos(R2)
					Mvt(R1) ≠ Mvt(R2)
				))
				∀(R ∈ Rames).(and(
					Dir(R)=-1
					Pos(R)=0
				) ⇒ Mvt(R)=0)
				∀(R ∈ Rames).(and(
					Dir(R)=1
					Pos(R)=NS1
				) ⇒ Mvt(R)=0)
			)
		)

	INITIALISATION
		Pos(1) := 0 ||
		Pos(2) := 1 ||
		Dir(1) := 1 ||
		Dir(2) := -1 ||
		Mvt(1) := 0 ||
		Mvt(2) := 0 ||
		Portes(1) := fermees ||
		Portes(2) := fermees

	EVENTS

		Depart_Station ≝
			ANY
				R ∈ Rames
			WHERE
				and(
					Portes(R)=refermees
					Mvt(R)=0
					or(
						Pos(R) ≠ NS1
						Dir(R) ≠ 1
					)
					or(
						Pos(R) ≠ 0
						Dir(R) ≠ -1
					)
					∀(R1 ∈ Rames).(R1 ≠ R ⇒ or(
						Pos(R1) ≠ Pos(R)
						Dir(R1) ≠ Dir(R)
					))
				)
			THEN
				Portes(R) := fermees ||
				Mvt(R) := 1
			END

		Arrivee_Station ≝
			ANY
				R ∈ Rames
			WHERE
				and(
					Mvt(R)=1
					∀(R1 ∈ Rames).(and(
						R1 ≠ R
					) ⇒ or(
						Pos(R1) ≠ (Pos(R) + Dir(R))
						Dir(R1) ≠ Dir(R)
					))
				)
			THEN
				Pos(R) := (Pos(R) + Dir(R)) ||
				Mvt(R) := 0
			END

		Changement_Direction ≝
			ANY
				R ∈ Rames
			WHERE
				and(
					Portes(R)=refermees
					or(
						and(
							Pos(R)=NS1
							Dir(R)=1
						)
						and(
							Pos(R)=0
							Dir(R)=-1
						)
					)
					∀(R1 ∈ Rames).(and(
						R1 ≠ R
					) ⇒ and(
						Pos(R1) ≠ Pos(R)
					))
				)
			THEN
				Portes(R) := fermees ||
				Dir(R) := (-1 * Dir(R))
			END

		Ouverture_Portes ≝
			ANY
				R ∈ Rames
			WHERE
				and(
					Portes(R)=fermees
					Mvt(R)=0
				)
			THEN
				Portes(R) := ouvertes
			END

		Fermeture_Portes ≝
			ANY
				R ∈ Rames
			WHERE
				and(
					Portes(R)=ouvertes
					Mvt(R)=0
				)
			THEN
				Portes(R) := refermees
			END

