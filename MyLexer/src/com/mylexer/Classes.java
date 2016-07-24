package com.mylexer;

public class Classes {
	
	//标识符分为关键字、预定义标识符、用户标识符
	public final static String KEYWORD = "关键字";
	public final static String PREDEFINE_IDENTIFIER = "预定义标识符";
	public final static String USER_IDENTIFIER = "用户标识符";
	
	//对用户定义的字符串变量进行分类分析
	public final static String LEGAL_IDENTIFIER = "合法标识符";
	public final static String ILLEGAL_IDENTIFIER = "非法标识符";
	public final static String VALUE = "数值";
	
	//对一些符号进行分类分析
	public final static String ESC = "转义字符";	//类似于"\n","\t"等
	public final static String FORMAT = "格式符";	//类似于"%d"
	public final static String OPERATOR = "运算符";	//"==",">=","++","*="
	public final static String SPECIALCHARACTER = "特殊字符";
	
	//预定义标识符
	public final static String MAIN = "main";
	public final static String INCLUDE = "include";
	public final static String SCANF = "scanf";
	public final static String PRINTF = "printf";
	public final static String DEFINE = "define";
	public final static String STDIO = "stdio";
	public final static String STDLIB = "stdlib";
	public final static String STRING = "string";
	//关键字
	public final static String AUTO = "auto";
	public final static String BREAK = "break";
	public final static String CASE = "case";
	public final static String CHAR = "char";
	public final static String CONST = "const";
	public final static String CONTINUE = "continue";
	public final static String DEFAULT = "default";
	public final static String DO = "do";
	public final static String DOUBLE = "double";
	public final static String ELSE = "else";
	public final static String ENUM = "enum";
	public final static String EXTERN = "extern";
	public final static String FLOAT = "float";
	public final static String FOR = "for";
	public final static String GOTO = "goto";
	public final static String IF = "if";
	public final static String INT = "int";
	public final static String LONG = "long";
	public final static String REGISTER = "register";
	public final static String RETURN = "return";
	public final static String SHORT = "short";
	public final static String SIGNED = "signed";
	public final static String SIZEOF = "sizeof";
	public final static String STATIC = "static";
	public final static String SWITCH = "switch";
	public final static String TYPEDEF = "typedef";
	public final static String UNION = "union";
	public final static String UNSIGNED = "unsigned";
	public final static String VOID = "void";
	public final static String VOLATILE = "volatile";
	public final static String WHILE = "while";
	//特殊字符
	public final static char ADD = '+'; 
	public final static char SUBTRACT = '-'; 	
	public final static char MULTIPLY = '*'; 	
	public final static char DEVIDE = '/'; 	
	public final static char SMALL = '<'; 			
	public final static char BIG = '>'; 	
	public final static char EQUAL = '='; 	
	public final static char HUICHE = '\n'; 	
	public final static char SPACE = ' ';
	public final static char EXCALMATORY = '!';	
	public final static char JINGHAO = '#'; 			
	public final static char PERCENT = '%';		
	public final static char POWER = '^'; 		
	public final static char ADDRESS = '&'; 		
	public final static char SLASH = '\\';			
	public final static char YOUHUA = '}';
	public final static char ZUOHUA = '{';
	public final static char YOUFANG = ']';
	public final static char ZUOFANG = '[';
	public final static char YOUYUAN = ')';
	public final static char ZUOYUAN = '(';
	public final static char SHUANGYING = '"';
	public final static char DANYING = '\'';
	public final static char WENHAO = '?';
	public final static char FENHAO = ';';
	public final static char MAOHAO = ':';
	public final static char DOUHAO = ',';
	public final static char POINT = '.';
	public final static char GANG = '|';
	
}
