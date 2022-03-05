#include <reg52.h> 
#include <intrins.h>
#include <stdio.h>
#include  <math.h>    //Keil library  
#include "delay.h"
//	操作方法
// 蓝牙发送字符 CX或cX 表示关闭x位灯， x取值1-8
// 蓝牙发送字符 OX或oX 表示关闭x位灯， x取值1-8
sbit LED1=P1^0;	  	//初始化led灯对应引脚
sbit LED2=P1^1;
sbit LED3=P1^2;
sbit LED4=P1^3;
sbit LED5=P1^4;
sbit LED6=P1^5;
sbit LED7=P1^6;
sbit LED8=P1^7;

#define INIT 0xFF	   //常量定义
#define OPEN 0x02
#define CLOSE 0x03
#define DAGN01 0x04
#define DAGN02 0x05
#define DAGN03 0x06						   

unsigned long times_5ms=0xaaaaaaaa;		 	//定时器计数
unsigned char Commd_Flag=INIT;		//命令接受标识

unsigned char pwmLed01 = 3; //pwm调整参数
unsigned char pwmLed02 = 3; //pwm调整参数
unsigned char pwmLed03 = 3; //pwm调整参数
unsigned char pwmLed04 = 3; //pwm调整参数
unsigned char pwmLed05 = 3; //pwm调整参数
unsigned char pwmLed06 = 3; //pwm调整参数
unsigned char pwmLed07 = 3; //pwm调整参数
unsigned char pwmLed08 = 3; //pwm调整参数

unsigned char countLed01 = 0;//pwm计数 
unsigned char countLed02 = 0;//pwm计数
unsigned char countLed03 = 0;//pwm计数
unsigned char countLed04 = 0;//pwm计数
unsigned char countLed05 = 0;//pwm计数
unsigned char countLed06 = 0;//pwm计数
unsigned char countLed07 = 0;//pwm计数
unsigned char countLed08 = 0;//pwm计数

void Init_Timer0(void);				//函数声明
void UART_Init(void);
void SendByte(unsigned char dat);
void SendStr(unsigned char *s,unsigned char length);

void main (void)
{
	Init_Timer0();        //定时器0初始化
	UART_Init();		   //蓝牙 串口 波特率9600
	
	LED1=0;	
 	DelayMs(100); 
	LED1=1;	//关闭相应的灯 并恢复命令标志
	LED2=0;	
 	DelayMs(100); 
	LED2=1;	//关闭相应的灯 并恢复命令标志	
	LED3=0;	
 	DelayMs(100); 
	LED3=1;	//关闭相应的灯 并恢复命令标志	
	LED4=0;	
 	DelayMs(100); 
	LED4=1;	//关闭相应的灯 并恢复命令标志		
	LED5=0;	
 	DelayMs(100); 
	LED5=1;	//关闭相应的灯 并恢复命令标志
	LED6=0;	
 	DelayMs(100); 
	LED6=1;	//关闭相应的灯 并恢复命令标志	
	LED7=0;	
 	DelayMs(100); 
	LED7=1;	//关闭相应的灯 并恢复命令标志	
	LED8=0;
 	DelayMs(100); 	
	LED8=1;	

	DelayMs(10);          //延时有助于稳定

	P1=0x00;
	while(1)         //主循环
	{
			//蓝牙的接收处理 均在中断中处理 请查看串口中断

		countLed01++;
		if(countLed01<pwmLed01)	   // 占空比调节
		{LED1=0;}			  //打开	
		else if(countLed01<=10)	//关闭时间段
		{
		 	LED1=1;			//关闭
			if(countLed01 == 10)	 countLed01=0;  //一个周期结束
		}

		countLed02++;
		if(countLed02<pwmLed02)	   //占空比调节
		{LED2=0;}			  //打开	
		else if(countLed02<=10)	//关闭时间段
		{
		 	LED2=2;			//关闭
			if(countLed02 == 10)	 countLed02=0;  //一个周期结束
		}

		countLed03++;
		if(countLed03<pwmLed03)	   //占空比调节
		{LED3=0;}			  //打开	
		else if(countLed03<=10)	//关闭时间段
		{
		 	LED3=1;			//关闭
			if(countLed03 == 10)	 countLed03=0;  //一个周期结束
		}


		countLed04++;
		if(countLed04<pwmLed04)	   //占空比调节
		{LED4=0;}			  //打开	
		else if(countLed04<=10)	//关闭时间段
		{
		 	LED4=1;			//关闭
			if(countLed04 == 10)	 countLed04=0;  //一个周期结束
		}


		countLed05++;
		if(countLed05<pwmLed05)	   //占空比调节
		{LED5=0;}			  //打开	
		else if(countLed05<=10)	//关闭时间段
		{
		 	LED5=1;			//关闭
			if(countLed05 == 10)	 countLed05=0;  //一个周期结束
		}

		countLed06++;
		if(countLed06<pwmLed06)	   //占空比调节
		{LED6=0;}			  //打开	
		else if(countLed06<=10)	//关闭时间段
		{
		 	LED6=1;			//关闭
			if(countLed06 == 10)	 countLed06=0;  //一个周期结束
		}

		countLed07++;
		if(countLed07<pwmLed07)	   // 占空比调节
		{LED7=0;}			  //打开	
		else if(countLed07<=10)	//关闭时间段
		{
		 	LED7=1;			//关闭
			if(countLed07 == 10)	 countLed07=0;  //一个周期结束
		}

		countLed08++;
		if(countLed08<pwmLed08)	   // 占空比调节
		{LED8=0;}			  //打开	
		else if(countLed08<=10)	//关闭时间段
		{
		 	LED8=1;			//关闭
			if(countLed08 == 10)	 countLed08=0;  //一个周期结束
		}

	}
}

void Init_Timer0(void)  //初始化定时器
{
	TMOD |= 0x01;	  //使用模式1，16位定时器，使用"|"符号可以在使用多个定时器时不受影响		     
	TH0=(65536-10000)/256;		  //重新赋值 20ms
	TL0=(65536-10000)%256;
	EA=1;            //总中断打开
	ET0=1;           //定时器中断打开
	TR0=1;           //定时器开关打开
}
void UART_Init(void)	//初始化串口
{
    SCON  = 0x50;		        // SCON: 模式 1, 8-bit UART, 使能接收  
    TMOD |= 0x20;               // TMOD: timer 1, mode 2, 8-bit 重装
    TH1   = 0xFD;               // TH1:  重装值 9600 波特率 晶振 11.0592MHz
	TL1 = TH1;  
    TR1   = 1;                  // TR1:  timer 1 打开                         
    EA    = 1;                  //打开总中断
    ES    = 1;                  //打开串口中断
} 

void Timer0_isr(void) interrupt 1 		//定时器中断
{
	TH0=(65536-5000)/256;		  //重新赋值 5ms
	TL0=(65536-5000)%256;
	times_5ms++;

}

void UART_SER (void) interrupt 4 	//串行中断服务程序
{
	unsigned char R_buf;
	if(RI)                        //判断是接收中断产生
	{
		RI=0;                      //标志位清零
		R_buf=SBUF;
		if((R_buf=='O')||(R_buf=='o'))
		{
			Commd_Flag=OPEN;	//接收到打开灯标志
		}
		else if((R_buf=='S')||(R_buf=='s'))
		{
			Commd_Flag=CLOSE;	//接收到 关闭灯标志
		}
		else if((R_buf=='A')||(R_buf=='a'))
		{				  
			Commd_Flag=DAGN01;	//接收到 关闭灯标志
		}
		else if((R_buf=='B')||(R_buf=='b'))
		{
			Commd_Flag=DAGN02;	//接收到 关闭灯标志
		}
		else if((R_buf=='C')||(R_buf=='c'))
		{
			Commd_Flag=DAGN03;	//接收到 关闭灯标志
		}
		else if((R_buf!='1')&&(R_buf!='2')&&(R_buf!='3')&&(R_buf!='4')&&(R_buf!='5')&&(R_buf!='6')&&(R_buf!='7')&&(R_buf!='8')&&(R_buf!='N'))
		{
			Commd_Flag=INIT;	 //否则 初始化接受标志
		}
		if((Commd_Flag==OPEN)||(Commd_Flag==DAGN01))			//根据命令值进行打开相应的灯
		{
			switch(R_buf)
			{
				case '1':pwmLed01=3;Commd_Flag=INIT;break;	//打开相应的灯 并恢复命令标志
				case '2':pwmLed02=3;Commd_Flag=INIT;break;			
				case '3':pwmLed03=3;Commd_Flag=INIT;break;			
				case '4':pwmLed04=3;Commd_Flag=INIT;break;			
				case '5':pwmLed05=3;Commd_Flag=INIT;break;	
				case '6':pwmLed06=3;Commd_Flag=INIT;break;			
				case '7':pwmLed07=3;Commd_Flag=INIT;break;			
				case '8':pwmLed08=3;Commd_Flag=INIT;break;
				case 'N':pwmLed01=3;pwmLed02=3;pwmLed03=3;pwmLed04=3;pwmLed05=3;pwmLed06=3;pwmLed07=3;pwmLed08=3;Commd_Flag=INIT;break;
				default:break;						 //此处错误判断 不可恢复命令标准
			}
		}
		else if(Commd_Flag==CLOSE)//根据命令值进行关闭相应的灯
		{
			switch(R_buf)
			{
				case '1':pwmLed01=1;Commd_Flag=INIT;break;	//打开相应的灯 并恢复命令标志
				case '2':pwmLed02=1;Commd_Flag=INIT;break;			
				case '3':pwmLed03=1;Commd_Flag=INIT;break;			
				case '4':pwmLed04=1;Commd_Flag=INIT;break;			
				case '5':pwmLed05=1;Commd_Flag=INIT;break;	
				case '6':pwmLed06=1;Commd_Flag=INIT;break;			
				case '7':pwmLed07=1;Commd_Flag=INIT;break;			
				case '8':pwmLed08=1;Commd_Flag=INIT;break;
				case 'N':pwmLed01=1;pwmLed02=1;pwmLed03=1;pwmLed04=1;pwmLed05=1;pwmLed06=1;pwmLed07=1;pwmLed08=1;Commd_Flag=INIT;break;
				default:break;						 //此处错误判断 不可恢复命令标准
			}
		}
		else if(Commd_Flag==DAGN02)//根据命令值进行关闭相应的灯
		{
			switch(R_buf)
			{
				case '1':pwmLed01=6;Commd_Flag=INIT;break;	//打开相应的灯 并恢复命令标志
				case '2':pwmLed02=6;Commd_Flag=INIT;break;			
				case '3':pwmLed03=6;Commd_Flag=INIT;break;			
				case '4':pwmLed04=6;Commd_Flag=INIT;break;			
				case '5':pwmLed05=6;Commd_Flag=INIT;break;	
				case '6':pwmLed06=6;Commd_Flag=INIT;break;			
				case '7':pwmLed07=6;Commd_Flag=INIT;break;			
				case '8':pwmLed08=6;Commd_Flag=INIT;break;				
				default:break;						 //此处错误判断 不可恢复命令标准
			}
		}
		else if(Commd_Flag==DAGN03)//根据命令值进行关闭相应的灯
		{
			switch(R_buf)
			{
				case '1':pwmLed01=9;Commd_Flag=INIT;break;	//打开相应的灯 并恢复命令标志
				case '2':pwmLed02=9;Commd_Flag=INIT;break;			
				case '3':pwmLed03=9;Commd_Flag=INIT;break;			
				case '4':pwmLed04=9;Commd_Flag=INIT;break;			
				case '5':pwmLed05=9;Commd_Flag=INIT;break;	
				case '6':pwmLed06=9;Commd_Flag=INIT;break;			
				case '7':pwmLed07=9;Commd_Flag=INIT;break;			
				case '8':pwmLed08=9;Commd_Flag=INIT;break;				
				default:break;						 //此处错误判断 不可恢复命令标准
			}
		}
		SBUF=R_buf;				   //返回接收到的数据
	}
	if(TI)  //如果是发送标志位，清零
	{							 
		TI=0;
	}
}

