grammar CallCost;

file       : def+ ;
def        : '[' type=ID name=ID ']' (ID '=' exp)+
           | '[' type=ID ctor']' (ID '=' exp)+
           ;
ctor       : ID '(' ')' | ID '(' PARAM (',' PARAM)* ')';
exp        : '(' exp ')' | VALUE | PARAM | CALLPARAM | ID | inst | exp BINOP exp | if;
inst       : ID '(' exp (',' exp)* ')' ;
if         : 'if' exp 'then' exp 'else' exp ;
WS         : [ \t\r\n]+ -> skip ;
PARAM      : '$'[a-zA-Z0-9_]+;
CALLPARAM  : '#'[a-zA-Z0-9_]+;
ID         : [a-zA-Z0-9_]+ ;
VALUE      : [0-9]+ ' '* ('%' | 'cent' | 'year');
BINOP      : '+' | '-' | '*' | '/' | '<' | '<=' | '>' | '>=' | '==' ;
