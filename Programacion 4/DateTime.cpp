//DateTime.cpp

#include "DateTime.h"
#include "EMonthOutOfRange.h"
#include "EMinOutOfRange.h"
#include "EYearOutOfRange.h"
#include "EHourOutOfRange.h"
#include "EDayOutOfRange.h"
#include <iostream>
#include <iomanip>
#include <time.h>

using namespace std;

	DateTime::DateTime() {
		time_t rawtime;
		struct tm * timeinfo;
		time (&rawtime);
		timeinfo = localtime (&rawtime);

		this->min = timeinfo->tm_min; //minutes after the hour (0-59)
		this->hour = timeinfo->tm_hour; //hours since midnight (0-23)
		this->day = timeinfo->tm_mday; //day of the month (1-31)
		this->month = timeinfo->tm_mon + 1; // months since January  ((0-11) + 1 ) -> (1-12)
		this->year = timeinfo->tm_year + 1900; // elapsed years since 1900 + 1900 = actual year

	}

	// debe ser posible obtener una instancia de DateTime indicando o no la hora
	DateTime::DateTime( int year, int month, int day ) throw ( EYearOutOfRange, EMonthOutOfRange, EDayOutOfRange ) {

                if ( (year<0) || (year>9999) ) throw EYearOutOfRange();
                if ( (month<1) || (month>12) ) throw EMonthOutOfRange();
                if ( (day<1) || (day>30) ) throw EDayOutOfRange();
		this->min = 0;
		this->hour = 0;
		this->day = day;
		this->month = month;
		this->year = year;
	}

	DateTime::DateTime( int year, int month, int day, int hour, int min ) throw( EYearOutOfRange, EMonthOutOfRange, EDayOutOfRange, EHourOutOfRange, EMinOutOfRange) {
		if ( (year<0) || (year>9999) ) throw EYearOutOfRange();
                if ( (month<1) || (month>12) ) throw EMonthOutOfRange();
                if ( (day<1) || (day>30) ) throw EDayOutOfRange();
                if ( (hour<0) || (hour>23) ) throw EHourOutOfRange();
                if ( (min<0) || (min>59) ) throw EMinOutOfRange();
                this->min = min;
		this->hour = hour;
		this->day = day;
		this->month = month;
		this->year = year;
}

	DateTime& DateTime::operator= (const DateTime &dt) {
        this->min = dt.min;
        this->hour = dt.hour;
        this->day = dt.day;
        this->month = dt.month;
        this->year = dt.year;
        return *this;
	}

	bool DateTime::operator== (const DateTime &dt) const{
           return ( this->min == dt.min &&
            this->hour == dt.hour &&
            this->day == dt.day &&
            this->month == dt.month &&
            this->year == dt.year );
	}

	bool DateTime::operator!= ( const DateTime &dt ) const{
		return !( *this==dt );
	}

	bool DateTime::operator<( const DateTime& dt ) const {
      bool ret = false;

        if ( this->year < dt.year ) { //chequeo el anio
            ret = true;
        } else if ( this->year == dt.year )  { // chequeo el mes

            if ( this->month < dt.month ) {
                ret = true;
            } else if ( this->month == dt.month ) { //chequeo el dia

                if ( this->day < dt.day ) {
                    ret = true;
                } else if ( this->day == dt.day ) { //chequeo la hora

                    if ( this->hour < dt.hour ) {
                        ret = true;
                    } else if ( this->hour == dt.hour ) { //chequeo los min

                        if ( this->min < dt.min ) {
                            ret = true;
                        } else if ( this->min == dt.min ) { // si todo es igual, entonces son iguales, no menor
                            ret = false;
                        }
                    }
                }
            }
        }

        return ret;

	}

	bool DateTime::operator>( const DateTime& dt )  const{
            return dt<*this;
	}

	bool DateTime::operator<= (const DateTime &dt) const{
    return !( *this>dt );
	}

	bool DateTime::operator>= (const DateTime &dt) const{
    return !( *this<dt );
	}

	void DateTime::divint(bool Y,const int&dividendo,const int& divisor,int& cociente,int&resto) {
    cociente=dividendo/divisor;
    resto=dividendo%divisor;
    if (((resto<0)&&Y)||((resto<=0)&&!Y)){
        resto=divisor+resto;
        cociente--;
        };
	}

	DateTime DateTime::operator+ (const double &dias) const{
		DateTime Res;
		int A,Di;
		double D;
		Di=dias;
		D=dias-Di;

		DateTime::divint(true,(this->min+D*24*60),60,A,Res.min);
		DateTime::divint(true,A+this->hour,24,A,Res.hour);
		DateTime::divint(false,A+this->day+Di,30,A,Res.day);
		DateTime::divint(false,A+this->month,12,A,Res.month);
		Res.year=A+this->year;
    return (Res);
	}

	DateTime DateTime::operator- (const double &dias) const{
            return ((*this)+(-dias));
        }

	double DateTime::operator- (const DateTime &p) const{
            double res=this->day-p.day;
            res+=(this->month-p.month)*30;
            res+=(this->year-p.year)*360;
            res+=(this->hour-p.hour)/24.0;
            res+=(this->min-p.min)/1440.0;
            return res;
}

	ostream& operator<< (ostream &o , const DateTime &dt){
        //http://www.fredosaurus.com/notes-cpp/io/omanipulators.html
        o << setw(4) << setfill('0') << dt.year << "/"
          << setw(2) << setfill('0') << dt.month << "/"
          << setw(2) << setfill('0') << dt.day << " "
          << setw(2) << setfill('0') << dt.hour << ":"
          << setw(2) << setfill('0') << dt.min;

        return o;
}
