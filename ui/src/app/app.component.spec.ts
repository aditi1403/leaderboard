import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {TestBed, async} from '@angular/core/testing';
import {Platform} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';

import {AppComponent} from './app.component';
import {AuthService, AuthServiceConfig, GoogleLoginProvider, SocialLoginModule} from 'angular-6-social-login';
import {RouterTestingModule} from '@angular/router/testing';

describe('AppComponent', () => {
    let statusBarSpy, splashScreenSpy, platformReadySpy, platformSpy;

    beforeEach(async(() => {
        statusBarSpy = jasmine.createSpyObj('StatusBar', ['styleDefault']);
        splashScreenSpy = jasmine.createSpyObj('SplashScreen', ['hide']);
        platformReadySpy = Promise.resolve();
        platformSpy = jasmine.createSpyObj('Platform', {ready: platformReadySpy});

        TestBed.configureTestingModule({
            imports: [
                SocialLoginModule,
                RouterTestingModule],
            declarations: [AppComponent],
            schemas: [CUSTOM_ELEMENTS_SCHEMA],
            providers: [
                {provide: StatusBar, useValue: statusBarSpy},
                {provide: SplashScreen, useValue: splashScreenSpy},
                {provide: Platform, useValue: platformSpy},
                AuthService, {
                    provide: AuthServiceConfig,
                    useValue: new AuthServiceConfig([
                        {id: GoogleLoginProvider.PROVIDER_ID,
                            provider: new GoogleLoginProvider('id')} ])
                }
            ],
        }).compileComponents();
    }));

    it('should create the app', () => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });

    it('should initialize the app', async () => {
        TestBed.createComponent(AppComponent);
        expect(platformSpy.ready).toHaveBeenCalled();
        await platformReadySpy;
        expect(statusBarSpy.styleDefault).toHaveBeenCalled();
        expect(splashScreenSpy.hide).toHaveBeenCalled();
    });
});
