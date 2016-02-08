/**
 * Created by Erdal on 07.02.2016.
 */
$(document).ready(function(){
    $('#clock').thooClock({
        size:150,
        dialColor:'#000000',
        dialBackgroundColor:'transparent',

        secondHandColor:'#D39819',
        minuteHandColor:'#000000',
        hourHandColor:'#000000',
        alarmHandColor:'#FFFFFF',
        alarmHandTipColor:'#026729',

/* OPTIONS and CALLBACKS HERE
        secondHandColor:'#F3A829',
        minuteHandColor:'#FFFFFF',
        hourHandColor:'#FFFFFF',
        alarmHandColor:'#FFFFFF',
        alarmHandTipColor:'#026729',
 */
        hourCorrection:'+0',
        alarmCount:0,
        showNumerals:true
    });
});
