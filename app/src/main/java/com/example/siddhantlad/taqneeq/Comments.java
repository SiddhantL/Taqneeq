/*
for (int i=0;i<ticketsEvent.size();i++){
final int finalI = i;
        mTicket.child(ticketsEvent.get(i)).addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {
        int ticketsPerEvent=(int)dataSnapshot.getChildrenCount();
        for (int j=0;j<ticketsPerEvent;j++){
        ticketIDTally.add(ticketsEvent.get(finalI));
        //      Toast.makeText(getActivity(), ticketIDTally.get(0), Toast.LENGTH_SHORT).show();
        }
        for (int j = 0; j < ticketIDTally.size(); j++) {
final int finalJ = j;
        mTicket.child(ticketIDTally.get(j)).addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot1) {
        for (final DataSnapshot pd1 : dataSnapshot1.getChildren()) {
        mTicket.child(ticketIDTally.get(finalJ)).child(pd1.getKey()).addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {
        for (final DataSnapshot pd : dataSnapshot.getChildren()) {
        if (pd.getKey().equals("Name")) {
        ticketID.add(pd1.getKey());
        ticketType.add(pd.getValue().toString());
        } else if (pd.getKey().equals("Enters")) {
        entering.add(pd.getValue().toString());
        } else if (pd.getKey().equals("Divided")) {
        divided.add(pd.getValue().toString());
        }
        }
        Toast.makeText(getActivity(), Integer.toString(customExhibitionName.size())+" "+Integer.toString(ticketIDTally.size())+" "+Integer.toString(entering.size()), Toast.LENGTH_SHORT).show();
        if (customExhibitionName.size() == customExhibitionDate.size() && customExhibitionName.size() == ticketType.size() && customExhibitionDate.size() == entering.size() && customExhibitionName.size() == divided.size() && customExhibitionVenue.size() == customExhibitionTime.size() && customExhibitionVenue.size() == divided.size() && ticketID.size()==ticketIDTally.size()) {
        items.clear();
        recyclerView.removeAllViews();
        for (int i = 0; i < ticketsEvent.size(); i++) {
        items.add(new TicketModelClass(customExhibitionName.get(i), customExhibitionDate.get(i), entering.get(i), ticketType.get(i), divided.get(i), ticketsEvent.get(i), customExhibitionVenue.get(i), customExhibitionTime.get(i), ticketIDTally.get(i)));
        //Toast.makeText(getActivity(), customExhibitionName.get(i)+ customExhibitionDate.get(i)+ entering.get(i)+ ticketType.get(i)+ divided.get(i)+ ticketIDTally.get(i), Toast.LENGTH_SHORT).show();
        ticketAdapter.notifyDataSetChanged();
        }
        }
        }

@Override
public void onCancelled(DatabaseError databaseError) {

        }
        });
        }
        }

@Override
public void onCancelled(DatabaseError databaseError) {

        }
        });
        }


        }

@Override
public void onCancelled(DatabaseError databaseError) {

        }
        });
        }*/
