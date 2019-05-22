import { Component} from '@angular/core';
import {Room} from '../service/room';
import {ChartServiceService} from '../service/chart-service.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-rooms-list',
  templateUrl: './rooms-list.component.html',
  styleUrls: ['./rooms-list.component.scss']
})
export class RoomsListComponent {
  public rooms: Array<Room> = new Array<Room>();
  constructor(private service: ChartServiceService, private _Activatedroute: ActivatedRoute ) {
    this.getRooms();
  }

  title = 'teamx-frontend';

  public getRooms() {
    this.service.getRooms().subscribe(value => {
      this.rooms = value;
      console.log(value);
      // this.rooms.push(
      //   {
      //     roomId: 'afgsafg',
      //     createdAt: 'affda',
      //     finishedAt: 'adaf',
      //     players: undefined,
      //     startedAt: 'adsad',
      //   }
      // );
      // this.rooms.push(
      //   {
      //     roomId: 'afgsafg',
      //     createdAt: 'affda',
      //     finishedAt: 'adaf',
      //     players: undefined,
      //     startedAt: 'adsad',
      //   }
      // );
      // this.rooms.push(
      //   {
      //     roomId: 'afgsafg',
      //     createdAt: 'affda',
      //     finishedAt: 'adaf',
      //     players: undefined,
      //     startedAt: 'adsad',
      //   }
      // );
      // this.rooms.push(
      //   {
      //     roomId: 'afgsafg',
      //     createdAt: 'affda',
      //     finishedAt: 'adaf',
      //     players: undefined,
      //     startedAt: 'adsad',
      //   }
      // );
    });
  }


}
