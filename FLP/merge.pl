/*Merge two sorted lists with removing the double values*/
/*
mergeLists(a1...an; b1...bm) = {
	[],						a == [] and b == []
	b,						a == [] and b != []
	a,						a != [] and b == []
	a1 u mergeLists(a2...an; b1...bm),	a1 <= b1
	b1 u mergeLists(a1...an; b2...bm),	otherwise
}
*/
/*(i,i,o)*/
mergeLists([],[],[]).
mergeLists([],[H|T],[H|T]).
mergeLists([H|T],[],[H|T]).
mergeLists([H1|T1],[H2|T2],[H1|T3]) :- H1 =< H2, !, mergeLists(T1,[H2|T2],T3).
mergeLists([H1|T1],[H2|T2],[H2|T3]) :- H1 > H2, mergeLists([H1|T1],T2,T3).


