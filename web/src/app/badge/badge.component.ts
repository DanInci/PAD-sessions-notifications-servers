import {Component, Input, OnInit} from '@angular/core';
import {Room} from '../service/room';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss']
})
export class BadgeComponent implements OnInit {

  @Input() room: Room;

  info = false;
  danger = false;
  success = false;

  status = 'success';

  constructor() {
  }

  ngOnInit() {
    if (this.room.finishedAt) {
      this.danger = true;
    } else if (this.room.startedAt) {
      this.info = true;
    } else { this.success = true;}
  }

  getStatus(room: Room): string {
    if (room.finishedAt) {
      return 'Closed';
    } else if (room.startedAt) {
      return 'Pending';
    } else { return 'Waiting for players'; }
  }
}
