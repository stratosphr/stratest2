MACHINE elevator

	CONSTS
		NE ≝ 3
		LF ≝ (NE - 1)

	SETS
		Floors ≝ 0..LF
		DoorsState ≝ {ouvertes, fermees, refermees}

	VARS
		PC ∈ DoorsState
		Pos ∈ Floors
		Dir ∈ {-1, 1}

	FUNS
		BC : Floors -> 0..1
		BM : Floors -> 0..1
		BD : Floors -> 0..1
		PE : Floors -> DoorsState
		PE_ : Floors -> DoorsState
		BM_ : Floors -> 0..1
		BD_ : Floors -> 0..1
		BC_ : Floors -> 0..1

	INVARIANT
		and(
			and(
				NE=3
				LF=(NE - 1)
			)
			and(
				PC ∈ DoorsState
				Pos ∈ Floors
				Dir ∈ {-1, 1}
			)
			and(
				BC!0 ∈ 0..1
				BC!1 ∈ 0..1
				BC!2 ∈ 0..1
				BM!0 ∈ 0..1
				BM!1 ∈ 0..1
				BM!2 ∈ 0..1
				BD!0 ∈ 0..1
				BD!1 ∈ 0..1
				BD!2 ∈ 0..1
				PE!0 ∈ DoorsState
				PE!1 ∈ DoorsState
				PE!2 ∈ DoorsState
			)
			and(
				BM(LF)=0
				BD(0)=0
				∀(etage ∈ Floors).(and(
					etage ≠ Pos
				) ⇒ PE(etage)=fermees)
				PC=ouvertes ⇒ PE(Pos)=ouvertes
				∀(etage ∈ Floors).(PE(etage) ≠ refermees)
			)
		)

	INITIALISATION
		PC := fermees ||
		Pos := 0 ||
		Dir := 1 ||
		PE(0) := fermees ||
		PE(1) := fermees ||
		PE(2) := fermees ||
		BC(0) := 0 ||
		BC(1) := 0 ||
		BC(2) := 0 ||
		BM(0) := 0 ||
		BM(1) := 0 ||
		BM(2) := 0 ||
		BD(0) := 0 ||
		BD(1) := 0 ||
		BD(2) := 0

	EVENTS

		Appel_Montee_Etage ≝
			ANY
				etage ∈ 0..(LF - 1)
			WHERE
				BM(etage)=0
			THEN
				BM(etage) := 1
			END

		Appel_Descente_Etage ≝
			ANY
				etage ∈ 1..LF
			WHERE
				BD(etage)=0
			THEN
				BD(etage) := 1
			END

		Appel_Montee_Cabine ≝
			ANY
				etage ∈ (Pos + 1)..LF
			WHERE
				BC(etage)=0
			THEN
				BC(etage) := 1
			END

		Appel_Descente_Cabine ≝
			ANY
				etage ∈ 0..(Pos - 1)
			WHERE
				BC(etage)=0
			THEN
				BC(etage) := 1
			END

		Ouverture_Portes_Cabine ≝
			SELECT
				and(
					PE(Pos)=ouvertes
					PC=fermees
				)
			THEN
				PC := ouvertes
			END

		Fermeture_Portes_Cabine ≝
			SELECT
				and(
					PE(Pos)=ouvertes
					PC=ouvertes
				)
			THEN
				PC := refermees
			END

		Ouverture_Portes_Etage ≝
			SELECT
				and(
					PE(Pos)=fermees
					PC=fermees
				)
			THEN
				PE(Pos) := ouvertes
			END

		Fermeture_Portes_Etage ≝
			SELECT
				and(
					PE(Pos)=ouvertes
					PC=refermees
				)
			THEN
				PE(Pos) := fermees
			END

		Montee_Cabine ≝
			SELECT
				and(
					Pos < LF
					Pos ≥ 0
					PC=refermees
					PE(Pos)=fermees
					Dir=1
					or(
						∃(etage ∈ (Pos + 1)..LF).(BM(etage)=1)
						∃(etage ∈ (Pos + 1)..LF).(BC(etage)=1)
					)
				)
			THEN
				Pos := (Pos + Dir) ||
				BC((Pos + Dir)) := 0 ||
				BM((Pos + Dir)) := 0 ||
				PC := fermees
			END

		Descente_Cabine ≝
			SELECT
				and(
					Pos > 0
					Pos ≤ LF
					PC=refermees
					PE(Pos)=fermees
					Dir=-1
					or(
						∃(etage ∈ 0..(Pos - 1)).(BD(etage)=1)
						∃(etage ∈ 0..(Pos - 1)).(BC(etage)=1)
					)
				)
			THEN
				Pos := (Pos + Dir) ||
				BC((Pos + Dir)) := 0 ||
				BD((Pos + Dir)) := 0 ||
				PC := fermees
			END

		Changement_Direction ≝
			SELECT
				and(
					PC=refermees
					PE(Pos)=fermees
					or(
						and(
							Dir=1
							∀(etage ∈ (Pos + 1)..LF).(and(
								BM(etage)=0
								BC(etage)=0
							))
						)
						and(
							Dir=-1
							∀(etage ∈ 0..(Pos - 1)).(and(
								BD(etage)=0
								BC(etage)=0
							))
						)
					)
					or(
						and(
							Dir=-1
							∃(etage ∈ (Pos + 1)..LF).(or(
								BM(etage)=1
								BC(etage)=1
							))
						)
						and(
							Dir=1
							∃(etage ∈ 0..(Pos - 1)).(or(
								BD(etage)=1
								BC(etage)=1
							))
						)
					)
				)
			THEN
				Dir := (-1 * Dir)
			END

