//DateTime.h

#ifndef _DATETIME_H_
#define _DATETIME_H_

#include <iostream>
#include "EMonthOutOfRange.h"
#include "EMinOutOfRange.h"
#include "EYearOutOfRange.h"
#include "EHourOutOfRange.h"
#include "EDayOutOfRange.h"

using namespace std;

class DateTime {
	private:
		int year;
		int month;
		int day;
		int hour;
		int min;

		//funcion auxiliar
		static void divint(bool ,const int&,const int&, int&, int&);

	public:
		DateTime();
		DateTime(int,int,int) throw ( EYearOutOfRange, EMonthOutOfRange, EDayOutOfRange ); //  year, month, day 
		DateTime(int,int,int,int,int) throw ( EYearOutOfRange, EMonthOutOfRange, EDayOutOfRange, EHourOutOfRange, EMinOutOfRange);
		//int GetAnio();
		//int GetMes();
		//int GetDia();
		//int GetHora();
		//int GetMin();
		//~DateTime();
		bool operator== (const DateTime &) const;
		bool operator!= (const DateTime &) const;
		bool operator< ( const DateTime &) const ;
		bool operator> (const DateTime &) const;
		bool operator<= (const DateTime &) const;
		bool operator>= (const DateTime &) const;
		DateTime& operator= (const DateTime&);
        DateTime operator+ (const double&) const;
        DateTime operator- (const double&) const;
        double operator- (const DateTime&) const;
        friend ostream& operator<< (ostream&, const DateTime&);
};

#endif // _DATETIME_H_
