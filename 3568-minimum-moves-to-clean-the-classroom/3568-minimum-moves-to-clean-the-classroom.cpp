class Solution {
    struct State {
        int r, c, mask, energy;
    };
public:
    int minMoves(vector<string>& classroom, int energy) {

        int m = classroom.size();
        int n = classroom[0].size();
        int litIdx[20][20];
        memset(litIdx, -1, sizeof(litIdx));

        int staR =-1, staC =-1;
        int k=0;

        for(int r=0; r<m; ++r){
            for (int c=0;c<n; ++c){
                if(classroom[r][c] == 'S'){
                    staR =r;
                    staC =c;
                }
                else if (classroom[r][c] == 'L'){
                    litIdx[r][c] =k++;
                }
            }
        }
        
        if (k==0) return 0;
        int targetMask = (1<<k) -1;

        int visited[20][20][1024];
        memset(visited, -1, sizeof(visited));

        queue<State> q;
        q.push({staR, staC, 0, energy});
        visited[staR][staC][0] =energy;

        int dirs[4][2] ={{-1,0}, {1,0}, {0,-1}, {0,1}};
        int steps =0;

        while(!q.empty()){
            int size =q.size();

            for (int i=0; i<size; ++i){
                auto[r, c, mask, curEnergy] = q.front();
                q.pop();

                if (mask == targetMask){
                    return steps;
                }

                for(auto& d : dirs){
                    int nr = r+ d[0];
                    int nc = c+ d[1];

                    if(nr >= 0 && nr <m && nc >=0 && nc <n){
                        char cell = classroom[nr][nc];

                        if (cell == 'X') continue;

                        int nextEnergy = curEnergy -1;
                        if (nextEnergy <0) continue;

                        int nextMask = mask;
                        if(cell == 'L'){
                            nextMask |= (1 << litIdx[nr][nc]);
                        }

                        if (cell == 'R'){
                            nextEnergy = energy;
                        }

                        if(nextEnergy >visited[nr][nc][nextMask]){
                            visited[nr][nc][nextMask] =  nextEnergy;
                            q.push({nr, nc, nextMask, nextEnergy});
                        }
                    }
                }
            }

            steps++;
        }
        return -1;


    }
};