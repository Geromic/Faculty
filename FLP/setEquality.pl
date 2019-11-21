/*Write a predicate to test equality of two sets without using the set difference*/
/*
(i,i)
setEqual(a1...an, b1..bm) = {
	true,				a = [] and b != []
	false,				a1 not in b
        setEqual(a2...an,b1...mb),      otherwise
}
*/
/*
(i,i)
inList(e, l1...ln) = {
       false, l == []
       true, e == l1
       inList(e, l2...ln), otherwise
}
*/

inList(_,[]) :- false.
inList(E,[E|_]) :- !.
inList(E,[_|T]) :- inList(E,T).

setEqual([],[]).
setEqual([],[_|_]).
setEqual([H|T], S) :- inList(H,S), !, setEqual(T,S).

main(A,B) :- setEqual(A,B), setEqual(B,A).

