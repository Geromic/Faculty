/*Write a predicate to select the n-th element of a given list.*/
/*
selectNth(l1...ln, k, i) = {
	false,				l == []
	l1,				i == k
	selectNth(l2...ln, k, i+1),	otherwise
}
*/
/*(i,i,o)*/
selectNth([E|_],1,E).
selectNth([_|T],K,E) :- selectNth(T,K1,E), K is K1 + 1.
