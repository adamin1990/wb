package org.qii.kakuwb.ui.preference.filter;

import org.qii.kakuwb.R;
import org.qii.kakuwb.support.database.FilterDBTask;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * User: qii
 * Date: 12-9-21
 */
public class FilterKeywordFragment extends AbstractFilterFragment {

    @Override
    protected List<String> getDBDataImpl() {
        return FilterDBTask.getFilterKeywordList(FilterDBTask.TYPE_KEYWORD);
    }

    @Override
    protected void addFilterImpl(Collection<String> set) {
        FilterDBTask.addFilterKeyword(FilterDBTask.TYPE_KEYWORD, set);
    }

    @Override
    protected List<String> removeAndGetFilterListImpl(Collection<String> set) {
        return FilterDBTask.removeAndGetNewFilterKeywordList(FilterDBTask.TYPE_KEYWORD, set);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_menu_filterkeywordfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_common:
                Set<String> words = CommonAppDefinedFilterList.getDefinedFilterKeywordAndUserList();
                words.removeAll(list);
                addFilter(words);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
