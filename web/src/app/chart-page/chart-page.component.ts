import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Room} from '../service/room';
import {ChartServiceService} from '../service/chart-service.service';

@Component({
  selector: 'app-chart-page',
  templateUrl: './chart-page.component.html',
  styleUrls: ['./chart-page.component.scss']
})
export class ChartPageComponent implements OnInit, OnDestroy {
  private sub: any;
  id: string;
  room: Room;

  public chartType = 'bar';
  public chartDatasets: Array<any> = [
    { data: [], label: 'Score' }
  ];
  public chartLabels: Array<any> = [];
  public chartColors: Array<any> = [
    {
      backgroundColor: [
        // 'rgba(255, 99, 132, 0.2)',
        // 'rgba(54, 162, 235, 0.2)',
        // 'rgba(255, 206, 86, 0.2)',
        // 'rgba(75, 192, 192, 0.2)',
        // 'rgba(153, 102, 255, 0.2)',
        // 'rgba(255, 159, 64, 0.2)',
        // 'rgba(255, 159, 64, 0.2)',
      ],
      borderColor: [
        // 'rgba(255,99,132,1)',
        // 'rgba(54, 162, 235, 1)',
        // 'rgba(255, 206, 86, 1)',
        // 'rgba(75, 192, 192, 1)',
        // 'rgba(153, 102, 255, 1)',
        // 'rgba(255, 159, 64, 1)',
        // 'rgba(255, 159, 64, 1)',
      ],
      borderWidth: 2,
    }
  ];
  public chartOptions: any = {
    responsive: true
  };
  public chartHovered(e: any): void { }

  constructor(private route: ActivatedRoute,
            private chartServiceService: ChartServiceService) {}

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      console.log(this.id);
      this.getRoom(this.id);
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  getRoom(roomId: string) {
    this.chartServiceService.getRoom(roomId).subscribe(value => {
        this.room = value;
        console.log(value);
        for (const player of this.room.players) {
          this.chartLabels.push(player.name);
          this.chartDatasets[0].data.push(player.score);
          if (player.owner) {
            this.chartColors[0].backgroundColor.push('rgba(54, 162, 235, 0.2)');
            this.chartColors[0].borderColor.push('rgba(54, 162, 235, 1)');
          } else {
            this.chartColors[0].backgroundColor.push('rgba(255, 99, 132, 0.2)');
            this.chartColors[0].borderColor.push('rgba(255,99,132,1)');
          }
        }
      this.chartDatasets[0].data.push(0);
      });
  }

}
