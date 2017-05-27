import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from "@angular/common";

import { NavigationComponent } from './navigation.component';

@NgModule({
  imports: [BrowserModule, CommonModule],
  declarations: [
    NavigationComponent
  ],
  bootstrap: [NavigationComponent],
  exports: [NavigationComponent]
})
export class NavigationModule {}