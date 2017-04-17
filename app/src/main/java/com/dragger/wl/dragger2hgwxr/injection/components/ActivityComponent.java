/*
 *  Copyright (C) 2015 GuDong <gudong.name@gmail.com>
 *
 *  This file is part of GdTranslate
 *
 *  GdTranslate is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GdTranslate is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GdTranslate.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.dragger.wl.dragger2hgwxr.injection.components;

import com.dragger.wl.dragger2hgwxr.MainActivity;
import com.dragger.wl.dragger2hgwxr.injection.ActivityScope;
import com.dragger.wl.dragger2hgwxr.injection.modules.ActivityModule;
import com.dragger.wl.dragger2hgwxr.mvp.presenter.MainPresenter;

import dagger.Component;


/**
 * Created by GuDong on 2/28/16 10:42.
 * Contact with gudong.name@gmail.com.
 */
@ActivityScope
@Component(modules = {ActivityModule.class},dependencies = {AppComponent.class})
public interface ActivityComponent {
   MainActivity inject(MainActivity mainActivity);
//   MainPresenter presenter();

//   void inject(MainActivity activity);
   /*  void inject(WordsBookActivity activity);
    void inject(ProcessTextActivity activity);
    void inject(ListenClipboardService service);*/
    //void inject(AcknowledgementsActivity activity);
}
