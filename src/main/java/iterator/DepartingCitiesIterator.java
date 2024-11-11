package iterator;

import java.util.List;

public class DepartingCitiesIterator implements ExtendedIterator<String> {
    private List<String> cityList;
    private int position = 0;
    
    public DepartingCitiesIterator(List<String> cities) {
        this.cityList = cities;
    }
    
    @Override
    public boolean hasNext() {
        return position < cityList.size();
    }
    
    @Override
    public String next() {
        if (hasNext()) {
            return cityList.get(position++);
        }
        return null;
    }
    
    @Override
    public String previous() {
        if (hasPrevious()) {
            return cityList.get(--position);
        }
        return null;
    }
    
    @Override
    public boolean hasPrevious() {
        return position > 0;
    }
    
    @Override
    public void goFirst() {
        position = 0;
    }
    
    @Override
    public void goLast() {
        position = cityList.size();
    }
}