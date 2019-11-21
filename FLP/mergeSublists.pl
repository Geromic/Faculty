/*For a heterogeneous list, formed from integer numbers and list of numbers,
  merge all sublists with removing the double values*/
/*
subMerge(l1...ln; rez)
    rez1 is mergeLists(l1,rez) -> subMerge(l2..ln, rez1), if H is list.
    subMerge(l2...ln, rez),  otherwise
*/

/*
toSet(l1...ln, rez)
    rez.add(l1) -> toSet(l2...ln), l1 not in rez
    toSet(l2...ln), otherwise
*/
/*(i,o)*/

inList(E,[E|_]).
inList(E,[_|T]) :- inList(E,T).

toSet([],[]).
toSet([H|T],[H|R]) :- not(inList(H,T)), toSet(T,R).
toSet([H|T], R) :- inList(H,T), toSet(T,R).

mergeLists([],[],[]).
mergeLists([],[H|T],[H|T]).
mergeLists([H|T],[],[H|T]).
mergeLists([H1|T1],[H2|T2],[H1|T3]) :- H1 =< H2, !, mergeLists(T1,[H2|T2],T3).
mergeLists([H1|T1],[H2|T2],[H2|T3]) :- H1 > H2, mergeLists([H1|T1],T2,T3).

subMerge([], []).
subMerge([H|T], R1) :- is_list(H), subMerge(T,R), mergeLists(H,R,R1).
subMerge([_|T], R) :- subMerge(T,R).

main(L,R) :- subMerge(L,S), toSet(S,R).






