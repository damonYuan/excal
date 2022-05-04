README
====

tcal - Trading Calendar.

## Background

I was googling around and trying my luck to find some open api / free libs for Hong Kong Stock Market trading calendar while only this, the [Subscribe to the HKEX Calendar](https://www.hkex.com.hk/News/HKEX-Calendar/Subscribe-to-the-HKEX-Calendar?sc_lang=en), that really met my standard about the data real-time and accuracy. Although it's not completely a free lunch to be integrated or consumed by the application directly, a parser inside a trading calendar library should be easy enough considering the data structure provided by the subscription. 

```
BEGIN:VEVENT
DTSTART:20221226T020500Z
DTEND:20221226T020500Z
UID:{1330DBF6-C542-41B3-9B3C-A3919D1C0458}
SUMMARY:Stock Connect - Southbound - Trading is Closed
END:VEVENT

BEGIN:VEVENT
DTSTART;VALUE=DATE:20221227
DTEND;VALUE=DATE:20221227
UID:{DF5710E5-36B4-4145-B970-BD7FEA63F534}
SUMMARY:Hong Kong Public Holidays - The second weekday after Christmas Day
DESCRIPTION:Hong Kong Market is closed
END:VEVENT

```

Try `curl -L  http://www.hkex.com.hk/News/HKEX-Calendar/Subscribe-Calendar?sc_lang=en` and check the data. And more calendar data from different exchange can be leverage and implemented into this library in similar way.