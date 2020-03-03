set -x

sleep 5s

boot -main com.github.olekhnovych.ticketbooking.ManageSchema create
boot -main com.github.olekhnovych.ticketbooking.Boot
