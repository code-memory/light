#include <reg52.h> 
#include <intrins.h>
#include <stdio.h>
#include  <math.h>    //Keil library  
#include "delay.h"
//	��������
// ���������ַ� CX��cX ��ʾ�ر�xλ�ƣ� xȡֵ1-8
// ���������ַ� OX��oX ��ʾ�ر�xλ�ƣ� xȡֵ1-8
sbit LED1=P1^0;	  	//��ʼ��led�ƶ�Ӧ����
sbit LED2=P1^1;
sbit LED3=P1^2;
sbit LED4=P1^3;
sbit LED5=P1^4;
sbit LED6=P1^5;
sbit LED7=P1^6;
sbit LED8=P1^7;

#define INIT 0xFF	   //��������
#define OPEN 0x02
#define CLOSE 0x03
#define DAGN01 0x04
#define DAGN02 0x05
#define DAGN03 0x06						   

unsigned long times_5ms=0xaaaaaaaa;		 	//��ʱ������
unsigned char Commd_Flag=INIT;		//������ܱ�ʶ

unsigned char pwmLed01 = 3; //pwm��������
unsigned char pwmLed02 = 3; //pwm��������
unsigned char pwmLed03 = 3; //pwm��������
unsigned char pwmLed04 = 3; //pwm��������
unsigned char pwmLed05 = 3; //pwm��������
unsigned char pwmLed06 = 3; //pwm��������
unsigned char pwmLed07 = 3; //pwm��������
unsigned char pwmLed08 = 3; //pwm��������

unsigned char countLed01 = 0;//pwm���� 
unsigned char countLed02 = 0;//pwm����
unsigned char countLed03 = 0;//pwm����
unsigned char countLed04 = 0;//pwm����
unsigned char countLed05 = 0;//pwm����
unsigned char countLed06 = 0;//pwm����
unsigned char countLed07 = 0;//pwm����
unsigned char countLed08 = 0;//pwm����

void Init_Timer0(void);				//��������
void UART_Init(void);
void SendByte(unsigned char dat);
void SendStr(unsigned char *s,unsigned char length);

void main (void)
{
	Init_Timer0();        //��ʱ��0��ʼ��
	UART_Init();		   //���� ���� ������9600
	
	LED1=0;	
 	DelayMs(100); 
	LED1=1;	//�ر���Ӧ�ĵ� ���ָ������־
	LED2=0;	
 	DelayMs(100); 
	LED2=1;	//�ر���Ӧ�ĵ� ���ָ������־	
	LED3=0;	
 	DelayMs(100); 
	LED3=1;	//�ر���Ӧ�ĵ� ���ָ������־	
	LED4=0;	
 	DelayMs(100); 
	LED4=1;	//�ر���Ӧ�ĵ� ���ָ������־		
	LED5=0;	
 	DelayMs(100); 
	LED5=1;	//�ر���Ӧ�ĵ� ���ָ������־
	LED6=0;	
 	DelayMs(100); 
	LED6=1;	//�ر���Ӧ�ĵ� ���ָ������־	
	LED7=0;	
 	DelayMs(100); 
	LED7=1;	//�ر���Ӧ�ĵ� ���ָ������־	
	LED8=0;
 	DelayMs(100); 	
	LED8=1;	

	DelayMs(10);          //��ʱ�������ȶ�

	P1=0x00;
	while(1)         //��ѭ��
	{
			//�����Ľ��մ��� �����ж��д��� ��鿴�����ж�

		countLed01++;
		if(countLed01<pwmLed01)	   // ռ�ձȵ���
		{LED1=0;}			  //��	
		else if(countLed01<=10)	//�ر�ʱ���
		{
		 	LED1=1;			//�ر�
			if(countLed01 == 10)	 countLed01=0;  //һ�����ڽ���
		}

		countLed02++;
		if(countLed02<pwmLed02)	   //ռ�ձȵ���
		{LED2=0;}			  //��	
		else if(countLed02<=10)	//�ر�ʱ���
		{
		 	LED2=2;			//�ر�
			if(countLed02 == 10)	 countLed02=0;  //һ�����ڽ���
		}

		countLed03++;
		if(countLed03<pwmLed03)	   //ռ�ձȵ���
		{LED3=0;}			  //��	
		else if(countLed03<=10)	//�ر�ʱ���
		{
		 	LED3=1;			//�ر�
			if(countLed03 == 10)	 countLed03=0;  //һ�����ڽ���
		}


		countLed04++;
		if(countLed04<pwmLed04)	   //ռ�ձȵ���
		{LED4=0;}			  //��	
		else if(countLed04<=10)	//�ر�ʱ���
		{
		 	LED4=1;			//�ر�
			if(countLed04 == 10)	 countLed04=0;  //һ�����ڽ���
		}


		countLed05++;
		if(countLed05<pwmLed05)	   //ռ�ձȵ���
		{LED5=0;}			  //��	
		else if(countLed05<=10)	//�ر�ʱ���
		{
		 	LED5=1;			//�ر�
			if(countLed05 == 10)	 countLed05=0;  //һ�����ڽ���
		}

		countLed06++;
		if(countLed06<pwmLed06)	   //ռ�ձȵ���
		{LED6=0;}			  //��	
		else if(countLed06<=10)	//�ر�ʱ���
		{
		 	LED6=1;			//�ر�
			if(countLed06 == 10)	 countLed06=0;  //һ�����ڽ���
		}

		countLed07++;
		if(countLed07<pwmLed07)	   // ռ�ձȵ���
		{LED7=0;}			  //��	
		else if(countLed07<=10)	//�ر�ʱ���
		{
		 	LED7=1;			//�ر�
			if(countLed07 == 10)	 countLed07=0;  //һ�����ڽ���
		}

		countLed08++;
		if(countLed08<pwmLed08)	   // ռ�ձȵ���
		{LED8=0;}			  //��	
		else if(countLed08<=10)	//�ر�ʱ���
		{
		 	LED8=1;			//�ر�
			if(countLed08 == 10)	 countLed08=0;  //һ�����ڽ���
		}

	}
}

void Init_Timer0(void)  //��ʼ����ʱ��
{
	TMOD |= 0x01;	  //ʹ��ģʽ1��16λ��ʱ����ʹ��"|"���ſ�����ʹ�ö����ʱ��ʱ����Ӱ��		     
	TH0=(65536-10000)/256;		  //���¸�ֵ 20ms
	TL0=(65536-10000)%256;
	EA=1;            //���жϴ�
	ET0=1;           //��ʱ���жϴ�
	TR0=1;           //��ʱ�����ش�
}
void UART_Init(void)	//��ʼ������
{
    SCON  = 0x50;		        // SCON: ģʽ 1, 8-bit UART, ʹ�ܽ���  
    TMOD |= 0x20;               // TMOD: timer 1, mode 2, 8-bit ��װ
    TH1   = 0xFD;               // TH1:  ��װֵ 9600 ������ ���� 11.0592MHz
	TL1 = TH1;  
    TR1   = 1;                  // TR1:  timer 1 ��                         
    EA    = 1;                  //�����ж�
    ES    = 1;                  //�򿪴����ж�
} 

void Timer0_isr(void) interrupt 1 		//��ʱ���ж�
{
	TH0=(65536-5000)/256;		  //���¸�ֵ 5ms
	TL0=(65536-5000)%256;
	times_5ms++;

}

void UART_SER (void) interrupt 4 	//�����жϷ������
{
	unsigned char R_buf;
	if(RI)                        //�ж��ǽ����жϲ���
	{
		RI=0;                      //��־λ����
		R_buf=SBUF;
		if((R_buf=='O')||(R_buf=='o'))
		{
			Commd_Flag=OPEN;	//���յ��򿪵Ʊ�־
		}
		else if((R_buf=='S')||(R_buf=='s'))
		{
			Commd_Flag=CLOSE;	//���յ� �رյƱ�־
		}
		else if((R_buf=='A')||(R_buf=='a'))
		{				  
			Commd_Flag=DAGN01;	//���յ� �رյƱ�־
		}
		else if((R_buf=='B')||(R_buf=='b'))
		{
			Commd_Flag=DAGN02;	//���յ� �رյƱ�־
		}
		else if((R_buf=='C')||(R_buf=='c'))
		{
			Commd_Flag=DAGN03;	//���յ� �رյƱ�־
		}
		else if((R_buf!='1')&&(R_buf!='2')&&(R_buf!='3')&&(R_buf!='4')&&(R_buf!='5')&&(R_buf!='6')&&(R_buf!='7')&&(R_buf!='8')&&(R_buf!='N'))
		{
			Commd_Flag=INIT;	 //���� ��ʼ�����ܱ�־
		}
		if((Commd_Flag==OPEN)||(Commd_Flag==DAGN01))			//��������ֵ���д���Ӧ�ĵ�
		{
			switch(R_buf)
			{
				case '1':pwmLed01=3;Commd_Flag=INIT;break;	//����Ӧ�ĵ� ���ָ������־
				case '2':pwmLed02=3;Commd_Flag=INIT;break;			
				case '3':pwmLed03=3;Commd_Flag=INIT;break;			
				case '4':pwmLed04=3;Commd_Flag=INIT;break;			
				case '5':pwmLed05=3;Commd_Flag=INIT;break;	
				case '6':pwmLed06=3;Commd_Flag=INIT;break;			
				case '7':pwmLed07=3;Commd_Flag=INIT;break;			
				case '8':pwmLed08=3;Commd_Flag=INIT;break;
				case 'N':pwmLed01=3;pwmLed02=3;pwmLed03=3;pwmLed04=3;pwmLed05=3;pwmLed06=3;pwmLed07=3;pwmLed08=3;Commd_Flag=INIT;break;
				default:break;						 //�˴������ж� ���ɻָ������׼
			}
		}
		else if(Commd_Flag==CLOSE)//��������ֵ���йر���Ӧ�ĵ�
		{
			switch(R_buf)
			{
				case '1':pwmLed01=1;Commd_Flag=INIT;break;	//����Ӧ�ĵ� ���ָ������־
				case '2':pwmLed02=1;Commd_Flag=INIT;break;			
				case '3':pwmLed03=1;Commd_Flag=INIT;break;			
				case '4':pwmLed04=1;Commd_Flag=INIT;break;			
				case '5':pwmLed05=1;Commd_Flag=INIT;break;	
				case '6':pwmLed06=1;Commd_Flag=INIT;break;			
				case '7':pwmLed07=1;Commd_Flag=INIT;break;			
				case '8':pwmLed08=1;Commd_Flag=INIT;break;
				case 'N':pwmLed01=1;pwmLed02=1;pwmLed03=1;pwmLed04=1;pwmLed05=1;pwmLed06=1;pwmLed07=1;pwmLed08=1;Commd_Flag=INIT;break;
				default:break;						 //�˴������ж� ���ɻָ������׼
			}
		}
		else if(Commd_Flag==DAGN02)//��������ֵ���йر���Ӧ�ĵ�
		{
			switch(R_buf)
			{
				case '1':pwmLed01=6;Commd_Flag=INIT;break;	//����Ӧ�ĵ� ���ָ������־
				case '2':pwmLed02=6;Commd_Flag=INIT;break;			
				case '3':pwmLed03=6;Commd_Flag=INIT;break;			
				case '4':pwmLed04=6;Commd_Flag=INIT;break;			
				case '5':pwmLed05=6;Commd_Flag=INIT;break;	
				case '6':pwmLed06=6;Commd_Flag=INIT;break;			
				case '7':pwmLed07=6;Commd_Flag=INIT;break;			
				case '8':pwmLed08=6;Commd_Flag=INIT;break;				
				default:break;						 //�˴������ж� ���ɻָ������׼
			}
		}
		else if(Commd_Flag==DAGN03)//��������ֵ���йر���Ӧ�ĵ�
		{
			switch(R_buf)
			{
				case '1':pwmLed01=9;Commd_Flag=INIT;break;	//����Ӧ�ĵ� ���ָ������־
				case '2':pwmLed02=9;Commd_Flag=INIT;break;			
				case '3':pwmLed03=9;Commd_Flag=INIT;break;			
				case '4':pwmLed04=9;Commd_Flag=INIT;break;			
				case '5':pwmLed05=9;Commd_Flag=INIT;break;	
				case '6':pwmLed06=9;Commd_Flag=INIT;break;			
				case '7':pwmLed07=9;Commd_Flag=INIT;break;			
				case '8':pwmLed08=9;Commd_Flag=INIT;break;				
				default:break;						 //�˴������ж� ���ɻָ������׼
			}
		}
		SBUF=R_buf;				   //���ؽ��յ�������
	}
	if(TI)  //����Ƿ��ͱ�־λ������
	{							 
		TI=0;
	}
}

