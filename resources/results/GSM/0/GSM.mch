MACHINE GSM

	CONSTS
		MaxChv ≝ 3
		MaxUnblock ≝ 10

	SETS
		PERMISSION ≝ {always[0], chv1[1], chv2[2], adm[3], never[4]}
		VALUE ≝ {ltgTrue[5], ltgFalse[6]}
		FILES ≝ {nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}
		ENABLEDSTATUS ≝ {enabled[15], disabled[16]}
		BLOCKEDSTATUS ≝ {blocked[17], unblocked[18]}
		CODE ≝ {a1[19], a2[20], a3[21], a4[22]}
		DATA ≝ {nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}
		STATUSWORD ≝ {sw9000[29], sw9001[30], sw9400[31], sw9404[32], sw9804[33], sw9808[34], sw9840[35]}

	VARS
		currentFile ∈ {nofiles[7], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}
		currentDirectory ∈ {mf[8], dfgsm[9]}
		enabledChv1Status ∈ ENABLEDSTATUS[{enabled[15], disabled[16]}]
		sw ∈ STATUSWORD[{sw9000[29], sw9001[30], sw9400[31], sw9404[32], sw9804[33], sw9808[34], sw9840[35]}]
		ddOut ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]

	FUNS
		codeUnblock : {chv1[1], chv2[2]} -> CODE[{a1[19], a2[20], a3[21], a4[22]}]
		parentFiles : FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}] -> FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
		permissionRead : {eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]} -> PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
		permissionUpdate : {eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]} -> PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
		counterChv : {chv1[1], chv2[2]} -> 0..MaxChv[0, 1, 2, 3]
		counterUnblock : {chv1[1], chv2[2]} -> 0..MaxUnblock[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
		codeChv : {chv1[1], chv2[2]} -> CODE[{a1[19], a2[20], a3[21], a4[22]}]
		permissionSession : PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}] -> VALUE[{ltgTrue[5], ltgFalse[6]}]
		blockedChvStatus : {chv1[1], chv2[2]} -> BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
		blockedStatus : {chv1[1], chv2[2]} -> BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
		data : {eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]} -> DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
		codeUnblock_ : {chv1[1], chv2[2]} -> CODE[{a1[19], a2[20], a3[21], a4[22]}]
		parentFiles_ : FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}] -> FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
		permissionRead_ : {eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]} -> PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
		permissionUpdate_ : {eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]} -> PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
		counterChv_ : {chv1[1], chv2[2]} -> 0..MaxChv[0, 1, 2, 3]
		blockedChvStatus_ : {chv1[1], chv2[2]} -> BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
		counterUnblock_ : {chv1[1], chv2[2]} -> 0..MaxUnblock[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
		codeChv_ : {chv1[1], chv2[2]} -> CODE[{a1[19], a2[20], a3[21], a4[22]}]
		blockedStatus_ : {chv1[1], chv2[2]} -> BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
		permissionSession_ : PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}] -> VALUE[{ltgTrue[5], ltgFalse[6]}]
		data_ : {eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]} -> DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]

	INVARIANT
		and(
			and(
				MaxChv = 3
				MaxUnblock = 10
			)
			and(
				currentFile ∈ {nofiles[7], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}
				currentDirectory ∈ {mf[8], dfgsm[9]}
				enabledChv1Status ∈ ENABLEDSTATUS[{enabled[15], disabled[16]}]
				sw ∈ STATUSWORD[{sw9000[29], sw9001[30], sw9400[31], sw9404[32], sw9804[33], sw9808[34], sw9840[35]}]
				ddOut ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
			)
			and(
				codeUnblock!1 ∈ CODE[{a1[19], a2[20], a3[21], a4[22]}]
				codeUnblock!2 ∈ CODE[{a1[19], a2[20], a3[21], a4[22]}]
				parentFiles!7 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!8 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!9 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!10 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!11 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!12 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!13 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				parentFiles!14 ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
				permissionRead!10 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionRead!11 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionRead!12 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionRead!13 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionRead!14 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionUpdate!10 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionUpdate!11 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionUpdate!12 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionUpdate!13 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				permissionUpdate!14 ∈ PERMISSION[{always[0], chv1[1], chv2[2], adm[3], never[4]}]
				counterChv!1 ∈ 0..MaxChv[0, 1, 2, 3]
				counterChv!2 ∈ 0..MaxChv[0, 1, 2, 3]
				counterUnblock!1 ∈ 0..MaxUnblock[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
				counterUnblock!2 ∈ 0..MaxUnblock[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
				codeChv!1 ∈ CODE[{a1[19], a2[20], a3[21], a4[22]}]
				codeChv!2 ∈ CODE[{a1[19], a2[20], a3[21], a4[22]}]
				permissionSession!0 ∈ VALUE[{ltgTrue[5], ltgFalse[6]}]
				permissionSession!1 ∈ VALUE[{ltgTrue[5], ltgFalse[6]}]
				permissionSession!2 ∈ VALUE[{ltgTrue[5], ltgFalse[6]}]
				permissionSession!3 ∈ VALUE[{ltgTrue[5], ltgFalse[6]}]
				permissionSession!4 ∈ VALUE[{ltgTrue[5], ltgFalse[6]}]
				blockedChvStatus!1 ∈ BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
				blockedChvStatus!2 ∈ BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
				blockedStatus!1 ∈ BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
				blockedStatus!2 ∈ BLOCKEDSTATUS[{blocked[17], unblocked[18]}]
				data!10 ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
				data!11 ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
				data!12 ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
				data!13 ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
				data!14 ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
			)
			and(
				codeUnblock(chv1[1]) = a3[21]
				codeUnblock(chv2[2]) = a4[22]
				parentFiles(dfgsm[9]) = mf[8]
				parentFiles(eficcid[10]) = mf[8]
				parentFiles(eflp[11]) = dfgsm[9]
				parentFiles(efimsi[12]) = dfgsm[9]
				parentFiles(efkc[13]) = dfgsm[9]
				parentFiles(efad[14]) = dfgsm[9]
				permissionRead(eficcid[10]) = always[0]
				permissionRead(eflp[11]) = always[0]
				permissionRead(efimsi[12]) = chv1[1]
				permissionRead(efkc[13]) = chv1[1]
				permissionRead(efad[14]) = always[0]
				permissionUpdate(eficcid[10]) = never[4]
				permissionUpdate(eflp[11]) = chv1[1]
				permissionUpdate(efimsi[12]) = adm[3]
				permissionUpdate(efkc[13]) = chv1[1]
				permissionUpdate(efad[14]) = adm[3]
				counterChv(chv1[1]) = 0 <=> blockedChvStatus(chv1[1]) = blocked[17]
				counterChv(chv2[2]) = 0 <=> blockedChvStatus(chv2[2]) = blocked[17]
				counterUnblock(chv1[1]) = 0 <=> blockedChvStatus(chv1[1]) = blocked[17]
				counterUnblock(chv2[2]) = 0 <=> blockedChvStatus(chv2[2]) = blocked[17]
				or(
					currentFile = nofiles[7]
					parentFiles(currentFile) = currentDirectory
				)
			)
		)

	INITIALISATION
		currentFile := nofiles[7] ||
		currentDirectory := mf[8] ||
		counterChv(chv1[1]) := MaxChv ||
		counterChv(chv2[2]) := MaxChv ||
		counterUnblock(chv1[1]) := MaxUnblock ||
		counterUnblock(chv2[2]) := MaxUnblock ||
		codeChv(chv1[1]) := a1[19] ||
		codeChv(chv2[2]) := a2[20] ||
		enabledChv1Status := enabled[15] ||
		blockedChvStatus(chv1[1]) := unblocked[18] ||
		blockedChvStatus(chv2[2]) := unblocked[18] ||
		blockedStatus(chv1[1]) := unblocked[18] ||
		blockedStatus(chv2[2]) := unblocked[18] ||
		permissionSession(always[0]) := ltgTrue[5] ||
		permissionSession(chv1[1]) := ltgFalse[6] ||
		permissionSession(chv2[2]) := ltgFalse[6] ||
		permissionSession(adm[3]) := ltgFalse[6] ||
		permissionSession(never[4]) := ltgFalse[6] ||
		data(eficcid[10]) := d1[24] ||
		data(eflp[11]) := d2[25] ||
		data(efimsi[12]) := d3[26] ||
		data(efkc[13]) := d4[27] ||
		data(efad[14]) := d5[28] ||
		sw := sw9000[29] ||
		ddOut := nodata[23]

	EVENTS

		Reset ≝
			SELECT
				or(
					permissionSession(always[0]) = ltgTrue[5]
					permissionSession(always[0]) = ltgFalse[6]
				)
			THEN
				currentFile := nofiles[7] ||
				currentDirectory := mf[8] ||
				permissionSession(always[0]) := ltgTrue[5] ||
				permissionSession(chv1[1]) := ltgFalse[6] ||
				permissionSession(chv2[2]) := ltgFalse[6] ||
				permissionSession(adm[3]) := ltgFalse[6] ||
				permissionSession(never[4]) := ltgFalse[6]
			END

		Select_File ≝
			ANY
				ff ∈ FILES[{nofiles[7], mf[8], dfgsm[9], eficcid[10], eflp[11], efimsi[12], efkc[13], efad[14]}]
			WHERE
				true
			THEN
				IF
					or(
						ff = mf[8]
						ff = dfgsm[9]
					)
				THEN
					IF
						or(
							parentFiles(ff) = currentDirectory
							parentFiles(currentDirectory) = ff
							ff = mf[8]
						)
					THEN
						sw := sw9000[29] ||
						currentDirectory := ff ||
						currentFile := nofiles
					ELSE
						sw := sw9404[32]
					END
				ELSE
					IF
						parentFiles(ff) = currentDirectory
					THEN
						sw := sw9000[29] ||
						currentFile := ff
					ELSE
						sw := sw9404[32]
					END
				END
			END

		Read_Binary ≝
			SELECT
				or(
					currentFile = eficcid[10]
					currentFile = eflp[11]
					currentFile = efimsi[12]
					currentFile = efkc[13]
					currentFile = efad[14]
				)
			THEN
				IF
					currentFile = nofiles[7]
				THEN
					sw := sw9400[31]
				ELSE
					IF
						or(
							permissionSession(permissionRead(currentFile)) = ltgTrue[5]
							permissionRead(currentFile) = chv1[1]
							enabledChv1Status = disabled[16]
						)
					THEN
						sw := sw9000[29] ||
						ddOut := data(currentFile)
					ELSE
						sw := sw9804[33]
					END
				END
			END

		Update_Binary ≝
			ANY
				dd ∈ DATA[{nodata[23], d1[24], d2[25], d3[26], d4[27], d5[28]}]
			WHERE
				or(
					currentFile = eficcid[10]
					currentFile = eflp[11]
					currentFile = efimsi[12]
					currentFile = efkc[13]
					currentFile = efad[14]
				)
			THEN
				IF
					currentFile = nofiles[7]
				THEN
					sw := sw9400[31]
				ELSE
					IF
						or(
							permissionSession(permissionUpdate(currentFile)) = ltgTrue[5]
							permissionUpdate(currentFile) = chv1[1]
							enabledChv1Status = disabled[16]
						)
					THEN
						sw := sw9000[29] ||
						data(currentFile) := dd
					ELSE
						sw := sw9804[33]
					END
				END
			END

		Change_Chv ≝
			ANY
				cc ∈ {chv1[1], chv2[2]}
				oldCodeCc ∈ {a1[19], a2[20], a3[21], a4[22]}
				newCodeCc ∈ {a1[19], a2[20], a3[21], a4[22]}
			WHERE
				true
			THEN
				IF
					blockedChvStatus(cc) = blocked[17]
				THEN
					sw := sw9840[35]
				ELSE
					IF
						and(
							cc = chv1[1]
							enabledChv1Status = disabled[16]
						)
					THEN
						sw := sw9808
					ELSE
						IF
							codeChv(cc) = oldCodeCc
						THEN
							codeChv(cc) := newCodeCc ||
							counterChv(cc) := MaxChv ||
							permissionSession(cc) := ltgTrue[5] ||
							sw := sw9000[29]
						ELSE
							IF
								counterChv(cc) = 1
							THEN
								counterChv(cc) := 0 ||
								blockedChvStatus(cc) := blocked[17] ||
								permissionSession(cc) := ltgFalse[6] ||
								sw := sw9840[35]
							ELSE
								counterChv(cc) := (counterChv(cc) - 1) ||
								sw := sw9804[33]
							END
						END
					END
				END
			END

		Verify_Chv ≝
			ANY
				cc ∈ {chv1[1], chv2[2]}
				codeCc ∈ {a1[19], a2[20], a3[21], a4[22]}
			WHERE
				true
			THEN
				IF
					blockedChvStatus(cc) = blocked[17]
				THEN
					sw := sw9840[35]
				ELSE
					IF
						and(
							cc = chv1[1]
							enabledChv1Status = disabled[16]
						)
					THEN
						sw := sw9808[34]
					ELSE
						IF
							codeChv(cc) = codeCc
						THEN
							counterChv(cc) := MaxChv ||
							permissionSession(cc) := ltgTrue[5] ||
							sw := sw9000[29]
						ELSE
							IF
								counterChv(cc) = 1
							THEN
								counterChv(cc) := 0 ||
								blockedChvStatus(cc) := blocked[17] ||
								permissionSession(cc) := ltgFalse[6] ||
								sw := sw9840[35]
							ELSE
								counterChv(cc) := (counterChv(cc) - 1) ||
								sw := sw9804[33]
							END
						END
					END
				END
			END

