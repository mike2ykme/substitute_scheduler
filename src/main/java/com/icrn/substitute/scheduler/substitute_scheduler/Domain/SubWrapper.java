package com.icrn.substitute.scheduler.substitute_scheduler.Domain;

import com.icrn.substitutes.model.Availability;
import com.icrn.substitutes.model.AvailabilitySet;
import com.icrn.substitutes.model.StartEnd;
import com.icrn.substitutes.model.Substitute;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class SubWrapper {
    private long id;
    private String name, contactNumber, address;
    private Set<String> holidaysTaken, scheduledDays;
    private String sunStart, sunEnd, monStart, monEnd, tueStart, tueEnd, wedStart, wedEnd, thuStart, thuEnd, friStart, friEnd, satStart, satEnd;

    public static SubWrapper getWrapperFromSubstitute(Substitute sub){
        SubWrapper wrap = new SubWrapper();
        wrap.setName(sub.getName());
        wrap.setAddress(sub.getAddress());
        wrap.setId(sub.getId());
        wrap.setContactNumber(sub.getContactNumber());

        if (sub.getHolidayAvailability() !=null)
            wrap.setHolidaysTaken(new HashSet<>(sub.getHolidayAvailability().getAvailability()));

        if (sub.getScheduledTimes() != null)
            wrap.setScheduledDays(new HashSet<>(sub.getScheduledTimes().getAvailability()));

        if (sub.getRegularAvailability() != null){
            Map<Integer,StartEnd> avail = sub.getRegularAvailability().getMapAvailability();
            if (avail.get(0) != null){
                wrap.setSunStart(avail.get(0).getStart().toString());
                wrap.setSunEnd(avail.get(0).getEnd().toString());
            }

            if (avail.get(1) != null){
                wrap.setMonStart(avail.get(1).getStart().toString());
                wrap.setMonEnd(avail.get(1).getEnd().toString());
            }

            if (avail.get(2) != null){
                wrap.setTueStart(avail.get(2).getStart().toString());
                wrap.setTueEnd(avail.get(2).getEnd().toString());
            }

            if (avail.get(3) != null){
                wrap.setWedStart(avail.get(3).getStart().toString());
                wrap.setWedEnd(avail.get(3).getEnd().toString());
            }

            if (avail.get(4) != null){
                wrap.setThuStart(avail.get(4).getStart().toString());
                wrap.setThuEnd(avail.get(4).getEnd().toString());
            }

            if (avail.get(5) != null){
                wrap.setFriStart(avail.get(5).getStart().toString());
                wrap.setFriEnd(avail.get(5).getEnd().toString());
            }

            if (avail.get(6) != null){
                wrap.setSatStart(avail.get(6).getStart().toString());
                wrap.setSatEnd(avail.get(6).getEnd().toString());
            }

        }
        return wrap;
    }

    public Substitute getSubstituteFromWrapper(){
        Substitute substitute = new Substitute();
        substitute.setId(this.getId());
        substitute.setName(this.getName());
        substitute.setAddress(this.getAddress());
        substitute.setContactNumber(this.getContactNumber());

        if (this.getScheduledDays() != null){
            substitute.setScheduledTimes(new AvailabilitySet(this.getScheduledDays()));
        }else {
            substitute.setScheduledTimes(new AvailabilitySet());
        }


        if (this.getHolidaysTaken() != null){
            substitute.setHolidayAvailability(new AvailabilitySet(this.getHolidaysTaken()));
        } else {
            substitute.setHolidayAvailability(new AvailabilitySet());
        }

        HashMap<Integer, StartEnd> dailyAvailability = new HashMap<>();

        if ((this.getSunStart() != null && !this.getSunStart().equals(""))
                && (this.getSunEnd() != null && !this.getSunEnd().equals("")))
            dailyAvailability.put(0,
                    new StartEnd(
                            LocalTime.parse(this.getSunStart()),
                            LocalTime.parse(this.getSunEnd())));

        if ((this.getMonStart() != null && !this.getMonStart().equals(""))
                && (this.getMonEnd() != null && !this.getMonEnd().equals("")))
            dailyAvailability.put(1,
                    new StartEnd(
                            LocalTime.parse(this.getMonStart()),
                            LocalTime.parse(this.getMonEnd())));

        if ((this.getTueStart() != null && !this.getTueStart().equals(""))
                && (this.getTueEnd() != null && !this.getTueEnd().equals("")))
            dailyAvailability.put(2,
                    new StartEnd(
                            LocalTime.parse(this.getTueStart()),
                            LocalTime.parse(this.getTueEnd())));

        if ((this.getWedStart() != null && !this.getWedStart().equals(""))
                && (this.getWedEnd() != null && !this.getWedStart().equals("")))
            dailyAvailability.put(3,
                    new StartEnd(
                            LocalTime.parse(this.getWedStart()),
                            LocalTime.parse(this.getWedStart())));

        if ((this.getThuStart() != null && !this.getThuStart().equals(""))
                && (this.getThuEnd() != null && !this.getThuEnd().equals("")))
            dailyAvailability.put(4,
                    new StartEnd(
                            LocalTime.parse(this.getThuStart()),
                            LocalTime.parse(this.getThuEnd())));

        if ((this.getFriStart() != null && !this.getFriStart().equals(""))
                && (this.getFriEnd() != null && !this.getFriEnd().equals("")))
            dailyAvailability.put(5,
                    new StartEnd(
                            LocalTime.parse(this.getFriStart()),
                            LocalTime.parse(this.getFriEnd())));

        if ((this.getSatStart() != null && !this.getSatStart().equals(""))
                && (this.getSatEnd() != null && !this.getSatEnd().equals("")))
            dailyAvailability.put(6,
                    new StartEnd(
                            LocalTime.parse(this.getSatStart()),
                            LocalTime.parse(this.getSatEnd())));


            substitute.setRegularAvailability(new Availability(dailyAvailability));


        return substitute;
    }
}
